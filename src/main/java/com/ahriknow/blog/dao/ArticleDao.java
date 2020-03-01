package com.ahriknow.blog.dao;

import com.ahriknow.blog.entity.Article;
import com.ahriknow.blog.entity.Category;
import com.ahriknow.blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
    List<Article> findAllByCategory(Category category);

    Integer countByCategory(Category category);

    List<Article> findAllByTitleLike(String string);
}
