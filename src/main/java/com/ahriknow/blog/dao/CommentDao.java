package com.ahriknow.blog.dao;

import com.ahriknow.blog.entity.Article;
import com.ahriknow.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, String>, JpaSpecificationExecutor<CommentDao> {
    List<Comment> findByArticle(Article article);
}
