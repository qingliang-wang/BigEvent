package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ArticleMapper;
import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.service.ArticleService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        // 补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);

    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 1 创建pageBen对象
        PageBean<Article> pageBean = new PageBean<>();
        //2 开启分页查询 Pagehelper
        PageHelper.startPage(pageNum,pageSize);


        //3 调用maopper
        Map<String ,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as =  articleMapper.list(categoryId,state,userId);
        Page<Article> p = (Page<Article>) as;
        // 把数据填充到PageBean 中
        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public Article articleInfo(Integer id) {
        return articleMapper.articleInfo(id);

    }

    @Override
    public void articleUpdate(Article article) {
        articleMapper.articleUpdate(article);
    }

    @Override
    public void ArticleDelete(Integer id) {
        articleMapper.ArticleDelete(id);
    }
}
