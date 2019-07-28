package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;

public interface ArticleService {
    //查询所有的用户，分页查询
    List<Article> findAllArticle(Integer currentPage, Integer rows);
    //数据个数
    Integer countArticle();
    //添加
    void add(Article article);
    //修改
    void edit(Article article);
    //删除
    void del(Article article);
    //es
    List<Article> selectArticleByContent(String content);
}
