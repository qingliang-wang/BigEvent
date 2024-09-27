package com.itheima.mapper;

import com.itheima.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    List<Article> list(Integer categoryId, String state, Integer userId);

    @Select("select * from article where id =#{id}")
    Article articleInfo(Integer id);

    @Update("update article set title=#{title},content =#{content},cover_img = #{coverImg},state=#{state},category_id=#{categoryId} where id = #{id}")
    void articleUpdate(Article article);

    @Delete("DELETE FROM article WHERE id = #{id}")
    void ArticleDelete(Integer id);

}
