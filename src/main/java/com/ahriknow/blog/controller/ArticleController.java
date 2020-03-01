package com.ahriknow.blog.controller;

import com.ahriknow.blog.entity.Article;
import com.ahriknow.blog.entity.Tag;
import com.ahriknow.blog.entity.User;
import com.ahriknow.blog.service.ArticleService;
import com.ahriknow.blog.service.CategoryService;
import com.ahriknow.blog.service.TagService;
import com.ahriknow.blog.utils.Auth;
import com.ahriknow.blog.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Auth auth;

    /**
     * 根据 id 查询文章
     *
     * @param id    文章 id
     * @param token token
     * @return 文章信息
     */
    @GetMapping(value = "/admin/article/{id}")
    public Response get(@PathVariable(value = "id") String id, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        Article article = articleService.findById(id);
        article.setCategoryId(article.getCategory().getId());
        List<String> tagIds = new ArrayList<>();
        for (Tag tag : (Iterable<Tag>) article.getTags()) {
            tagIds.add(tag.getId());
        }
        article.setTagIds(tagIds);
        return new Response(true, 200, "查询成功！", article);
    }

    /**
     * 获取文章列表
     *
     * @param token token
     * @return 文章列表
     */
    @GetMapping(value = "/admin/article")
    public Response getAll(@RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        List<Article> articles = articleService.findAll();
        return new Response(true, 200, "查询成功！", articles);
    }

    /**
     * 新建文章
     *
     * @param article 文章信息
     * @param token   token
     * @return 信息
     */
    @PostMapping(value = "/admin/article")
    public Response post(@RequestBody Article article, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        article.setUser(user);
        article.setCategory(categoryService.findById(article.getCategoryId()));
        article.setTags(tagService.findByIds(article.getTagIds()));
        articleService.insert(article);
        return new Response(true, 200, "保存成功！", null);
    }

    /**
     * 更新文章
     *
     * @param article 文章信息
     * @param id      文章 id
     * @param token   token
     * @return 信息
     */
    @PutMapping(value = "/admin/article/{id}")
    public Response put(@RequestBody Article article, @PathVariable(value = "id") String id, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        System.out.println(article.toString());
        articleService.update(article, id);
        return new Response(true, 200, "更新成功", null);
    }

    /**
     * 删除文章
     *
     * @param id    文章 id
     * @param token token
     * @return 信息
     */
    @DeleteMapping(value = "/admin/article/{id}")
    public Response delete(@PathVariable(value = "id") String id, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        articleService.delete(id);
        return new Response(true, 200, "删除成功！", null);
    }

    /*==================================================================================*/

    /**
     * 阅读根据 id 获取文章
     *
     * @param id 文章 id
     * @return 文章
     */
    @GetMapping(value = "/index/article/{id}")
    public Response getArticleById(@PathVariable(value = "id") String id) {
        return new Response(true, 200, "查询成功！", articleService.findById(id));
    }

    /**
     * 分页查询文章
     *
     * @param pageable 分页信息
     * @return 文章列表
     */
    @GetMapping(value = "/index/article")
    public Response getArticle(@PageableDefault(size = 2, sort = {"top", "updateDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Article> articles = articleService.findPage(pageable);
        return new Response(true, 200, "查询成功！", articles);
    }

    /**
     * 查询文章、分类、标签数量
     *
     * @return 数量数组
     */
    @GetMapping(value = "/index/count")
    public Response getCount() {
        return new Response(true, 200, "查询成功！", new ArrayList<>() {
            {
                add(articleService.count());
                add(categoryService.count());
                add(tagService.count());
            }
        });
    }

    /**
     * 搜索文章
     *
     * @param data 查询条件
     * @return 文章列表
     */
    @PostMapping(value = "/index/article")
    public Response search(@RequestBody Map<String, String> data) {
        if (data.containsKey("category") && !data.get("category").equals("")) {
            List<Article> articles = articleService.findAllByCategory(categoryService.findById(data.get("category")));
            return new Response(true, 200, "查询成功！", articles);
        } else if (data.containsKey("tag") && !data.get("tag").equals("")) {
            List<Article> articles = new ArrayList<>();
            for (Article art : articleService.findAll()) {
                for (Tag tag : art.getTags()) {
                    if (tag.getId().equals(data.get("tag")))
                        articles.add(art);
                }
            }
            return new Response(true, 200, "查询成功！", articles);
        } else {
            List<Article> articles = articleService.search(data.get("title"));
            return new Response(true, 200, "查询成功！", articles);
        }
    }
}
