package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository;
import io.goeasy.GoEasy;
import org.apache.commons.collections4.IterableUtils;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    //-----------------------------------------------------------
    //查询所有文章
    @Override
    public List<Article> findAllArticle(Integer currentPage, Integer rows) {
        //计算本页开始的第一条
        Integer begin = (currentPage - 1) * rows;
        //实现本页分页功能              开始位置   每页展示个数
        RowBounds rowBounds = new RowBounds(begin, rows);
        //创建user对象
        Article article = new Article();
        List<Article> articles = articleDao.selectByRowBounds(article, rowBounds);
        return articles;
    }

    //--------------------------------------------------------------------------------
    //总文章数
    @Override
    public Integer countArticle() {
        Article article = new Article();
        int count = articleDao.selectCount(article);
        return count;
    }

    //-----------------------------------------------------------
    //添加文章
    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        int i = articleDao.insertSelective(article);
        if (i == 0) {
            throw new RuntimeException("添加文章失败");
        } else {
            //第一个参数：REST Host       第二个参数：appks
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-268c25f44f5841ff93a8f4e6aa24193a");
            //第一个参数：channel的名称      第二个参数：发送的内容
            goEasy.publish("CmfzArticle", article.getContent());
            //添加到索引库中
            articleRepository.save(article);
        }
    }

    //---------------------------------------------------------
    //修改文章
    @Override
    public void edit(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        Article primaryKey = articleDao.selectByPrimaryKey(article.getId());
        primaryKey.setAuthor(article.getAuthor());
        primaryKey.setCreateDate(article.getCreateDate());
        primaryKey.setTitle(article.getTitle());
        primaryKey.setContent(article.getContent());
        articleRepository.save(primaryKey);
        if (i == 0) {
            throw new RuntimeException("修改文章失败");
        }
    }

    //---------------------------------------------------------
    //删除文章
    @Override
    public void del(Article article) {
        int i = articleDao.deleteByPrimaryKey(article.getId());
        articleRepository.deleteById(article.getId());
        if (i == 0) {
            throw new RuntimeException("文章删除失败");
        }
    }

    //------------------------------------------------------------
    //es代码
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<Article> selectArticleByContent(String content) {
        //判断是否为空串是否为null
        if ("".equals(content) || content == null) {
            Iterable<Article> all = articleRepository.findAll();
            List<Article> articles = IterableUtils.toList(all);
            return articles;
        } else {
            //高亮对象的创建
            HighlightBuilder.Field highlightBuilder = new HighlightBuilder
                    .Field("*")
                    .preTags("<span style='color:red'>")
                    .postTags("</span>")
                    .requireFieldMatch(false);
            //得到query并绑定
            NativeSearchQuery query = new NativeSearchQueryBuilder()
                    //                                  根据用户输入内容      指定字段，根据哪些查询
                    .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("author").field("content"))
                    //排序
                    .withSort(SortBuilders.scoreSort())
                    //高亮
                    .withHighlightFields(highlightBuilder)
                    .build();

            //                                                           前面创建的      实体类
            AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(query, Article.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                    SearchHits responseHits = response.getHits();
                    //所有的索引编号
                    SearchHit[] hits = responseHits.getHits();
                    List<Article> list = new ArrayList<>();
                    for (SearchHit hit : hits) {
                        Article article = new Article();
                        Map<String, Object> map = hit.getSourceAsMap();
                        article.setId(map.get("id").toString());
                        article.setTitle(map.get("title").toString());
                        article.setAuthor(map.get("author").toString());
                        article.setContent(map.get("content").toString());
                        //日期格式转换，如果long转换失败可以考虑format使用
                        String date = map.get("createDate").toString();
                        article.setCreateDate(new Date(Long.valueOf(date)));

//                        处理高亮
                        Map<String, HighlightField> map1 = hit.getHighlightFields();
                        if (map1.get("title") != null) {
                            article.setTitle(map1.get("title").getFragments()[0].toString());
                        }
                        if (map1.get("author") != null) {
                            article.setAuthor(map1.get("author").getFragments()[0].toString());
                        }
                        if (map1.get("content") != null) {
                            article.setContent(map1.get("content").getFragments()[0].toString());
                        }
                        list.add(article);
                    }
                    return new AggregatedPageImpl<T>((List<T>) list);
                }
            });
            List<Article> list = articles.getContent();
            return list;
        }
    }
}
