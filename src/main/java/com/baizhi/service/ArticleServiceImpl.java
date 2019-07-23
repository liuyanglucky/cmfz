package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.entity.User;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        Integer begin = (currentPage-1)*rows;
        //实现本页分页功能              开始位置   每页展示个数
        RowBounds rowBounds = new RowBounds(begin,rows);
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
        if(i == 0){
            throw new RuntimeException("添加文章失败");
        }else{
            //第一个参数：REST Host       第二个参数：appks
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io","BC-268c25f44f5841ff93a8f4e6aa24193a");
            //第一个参数：channel的名称      第二个参数：发送的内容
            goEasy.publish("CmfzArticle", article.getContent());
        }
    }
    //---------------------------------------------------------
    //修改文章
    @Override
    public void edit(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if(i == 0){
            throw new RuntimeException("修改文章失败");
        }
    }
    //---------------------------------------------------------
    //删除文章
    @Override
    public void del(Article article) {
        int i = articleDao.deleteByPrimaryKey(article.getId());
        if(i == 0){
            throw new RuntimeException("文章删除失败");
        }
    }
}
