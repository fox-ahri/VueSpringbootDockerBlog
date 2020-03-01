package com.ahriknow.blog.service;

import com.ahriknow.blog.dao.CommentDao;
import com.ahriknow.blog.entity.Article;
import com.ahriknow.blog.entity.Comment;
import com.ahriknow.blog.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 根据文章查询评论
     *
     * @param article 文章对象
     * @return 该评论列表
     */
    public List<Comment> findAll(Article article) {
        return commentDao.findByArticle(article);
    }

    /**
     * 保存评论
     *
     * @param comment 评论对象
     */
    public Comment insert(Comment comment) {
        String id = idGenerator.nextId();
        comment.setDate(new Date());
        comment.setId(id);
        commentDao.save(comment);
        return comment;
    }

    /**
     * 删除评论
     *
     * @param id 评论 id
     */
    public void delete(String id) {
        commentDao.deleteById(id);
    }

    /**
     * 删除多个评论
     *
     * @param comments 评论列表
     */
    public void deleteAll(List<Comment> comments) {
        commentDao.deleteInBatch(comments);
    }
}
