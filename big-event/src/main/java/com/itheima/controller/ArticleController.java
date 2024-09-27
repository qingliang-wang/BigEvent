package com.itheima.controller;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.ArticleService;
import com.itheima.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<String> list() {


        return Result.success("所有文章数据");
    }

    @PostMapping()
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum, Integer pageSize, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String state
    ) {
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);

    }

    @GetMapping("/detail")
    public Result<Article> articleInfo(Integer id) {
        Article article = articleService.articleInfo(id);

        return Result.success(article);

    }

    @PutMapping
    public Result articleUpdate(@RequestBody Article article) {
        articleService.articleUpdate(article);
        return Result.success("修改成功");
    }
    @DeleteMapping
    public Result ArticleDelete(Integer id)
    {
        articleService.ArticleDelete(id);
        return Result.success();
    }
}
