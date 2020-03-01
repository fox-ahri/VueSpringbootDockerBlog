package com.ahriknow.blog.controller;

import com.ahriknow.blog.entity.Comment;
import com.ahriknow.blog.entity.User;
import com.ahriknow.blog.service.ArticleService;
import com.ahriknow.blog.service.CommentService;
import com.ahriknow.blog.utils.Auth;
import com.ahriknow.blog.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private Auth auth;

    @GetMapping(value = "/admin/comment/{id}")
    public Response get(@PathVariable(value = "id") String id, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        List<Comment> comments = commentService.findAll(articleService.findById(id));
        return new Response(true, 200, "查询成功!", comments);
    }

    @DeleteMapping(value = "/admin/comment/{id}")
    public Response delete(@PathVariable(value = "id") String id, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        commentService.delete(id);
        return new Response(true, 200, "删除成功!", null);
    }

    @PostMapping(value = "/index/comment")
    public Response post(@RequestBody Comment comment) {
        comment.setArticle(articleService.findById(comment.getArticleId()));
        comment.setAvatar("http://ahriknow.oss-cn-beijing.aliyuncs.com/default_avatar.png");
        return new Response(true, 200, "回复成功!", commentService.insert(comment));
    }
}
