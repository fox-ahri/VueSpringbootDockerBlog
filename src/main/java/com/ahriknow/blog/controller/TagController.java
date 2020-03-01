package com.ahriknow.blog.controller;

import com.ahriknow.blog.entity.Category;
import com.ahriknow.blog.entity.Tag;
import com.ahriknow.blog.entity.User;
import com.ahriknow.blog.service.TagService;
import com.ahriknow.blog.utils.Auth;
import com.ahriknow.blog.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private Auth auth;

    @GetMapping(value = "/admin/tag")
    public Response get(@RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        List<Tag> tags = tagService.findAll();
        return new Response(true, 200, "查询成功！", tags);
    }

    @PostMapping(value = "/admin/tag")
    public Response post(@RequestBody Tag tag, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        tagService.insert(tag);
        return new Response(true, 200, "添加成功！", null);
    }

    @DeleteMapping(value = "/admin/tag/{id}")
    public Response delete(@PathVariable(value = "id") String id, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        tagService.delete(id);
        return new Response(true, 200, "删除成功！", null);
    }

    /*==================================================================================*/

    @GetMapping(value = "/index/tag")
    public Response getCategory() {
        List<Tag> tags = tagService.findAll();
        return new Response(true, 200, "查询成功！", tags);
    }
}
