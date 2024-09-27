package com.itheima.service;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.service.impl.ArticleServiceImpl;

public interface ArticleService  {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article articleInfo(Integer id);

    void articleUpdate(Article article);

    void ArticleDelete(Integer id);
}
