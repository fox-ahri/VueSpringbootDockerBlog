package com.ahriknow.blog.controller;

import com.ahriknow.blog.entity.Category;
import com.ahriknow.blog.entity.User;
import com.ahriknow.blog.service.ArticleService;
import com.ahriknow.blog.service.CategoryService;
import com.ahriknow.blog.service.UserService;
import com.ahriknow.blog.utils.Auth;
import com.ahriknow.blog.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private Auth auth;

    @GetMapping(value = "/admin/category")
    public Response get(@RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {
            category.setCount(articleService.countByCategory(category));
        }
        return new Response(true, 200, "查询成功！", categories);
    }

    @PostMapping(value = "/admin/category")
    public Response post(@RequestBody Category category, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        category.setUser(user);
        categoryService.insert(category);
        return new Response(true, 200, "添加成功！", null);
    }

    @PutMapping(value = "/admin/category")
    public Response put(@RequestBody Category category, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        category.setUser(user);
        categoryService.update(category);
        return new Response(true, 200, "更新成功！", null);
    }

    @DeleteMapping(value = "/admin/category/{id}")
    public Response delete(@PathVariable(value = "id") String id, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        categoryService.delete(id);
        return new Response(true, 200, "删除成功！", null);
    }

    /*==================================================================================*/

    @GetMapping(value = "/index/category")
    public Response getCategory() {
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {
            category.setCount(articleService.countByCategory(category));
        }
        return new Response(true, 200, "查询成功！", categories);
    }
}
