package com.ahriknow.blog.service;

import com.ahriknow.blog.dao.ArticleDao;
import com.ahriknow.blog.entity.Article;
import com.ahriknow.blog.entity.Category;
import com.ahriknow.blog.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 根据标题模糊查询
     *
     * @param title 查询条件
     * @return 查询列表
     */
    public List<Article> search(String title) {
        return articleDao.findAllByTitleLike("%" + title + "%");
    }

    /**
     * 根据分类查询文章
     *
     * @param category 分类对象
     * @return 文章列表
     */
    public List<Article> findAllByCategory(Category category) {
        return articleDao.findAllByCategory(category);
    }

    /**
     * 查询文章数
     *
     * @return 文章数
     */
    public long count() {
        return articleDao.count();
    }

    /**
     * 分页查询
     *
     * @param pageable 分页参数
     * @return 文章列表
     */
    public Page<Article> findPage(Pageable pageable) {
        return articleDao.findAll(pageable);
    }

    /**
     * 根据 id 查询文章
     *
     * @param id 文章 id
     * @return 文章对象
     */
    public Article findById(String id) {
        return articleDao.findById(id).orElse(null);
    }

    /**
     * 根据分类查询文章数量
     *
     * @param category 分类对象
     * @return 该分类文章数
     */
    public Integer countByCategory(Category category) {
        return articleDao.countByCategory(category);
    }

    /**
     * 查询所有文章
     *
     * @return 所有文章列表
     */
    public List<Article> findAll() {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "updateDate"));
        Sort sort = Sort.by(orders);
        return articleDao.findAll(sort);
    }

    /**
     * 保存新文章
     *
     * @param article 新文章对象
     */
    public void insert(Article article) {
        article.setId(idGenerator.nextId());
        System.out.println(article.toString());
        articleDao.save(article);
    }

    /**
     * 根据 id 更新文章
     *
     * @param article 新文章对象
     * @param id      文章 id
     */
    public void update(Article article, String id) {
        articleDao.save(article);
    }

    /**
     * 根据 id 删除文章
     *
     * @param id 文章 id
     */
    public void delete(String id) {
        articleDao.deleteById(id);
    }
}
