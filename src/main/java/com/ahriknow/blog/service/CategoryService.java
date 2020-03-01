package com.ahriknow.blog.service;

import com.ahriknow.blog.dao.CategoryDao;
import com.ahriknow.blog.entity.Category;
import com.ahriknow.blog.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 查询分类数
     *
     * @return 分类数
     */
    public long count() {
        return categoryDao.count();
    }

    /**
     * 根据 id 查询分类
     *
     * @param id 分类 id
     * @return 分类
     */
    public Category findById(String id) {
        return categoryDao.findById(id).orElse(null);
    }

    /**
     * 查询所有分类
     *
     * @return 所有分类列表
     */
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    /**
     * 保存新的分类
     *
     * @param category 新的分类
     */
    public void insert(Category category) {
        category.setId(idGenerator.nextId());
        categoryDao.save(category);
    }

    /**
     * 根据 id 更新分类
     *
     * @param category 新的分类
     */
    public void update(Category category) {
        categoryDao.save(category);
    }

    /**
     * 根据 id 查询分类
     *
     * @param id 分类 id
     */
    public void delete(String id) {
        categoryDao.deleteById(id);
    }
}
