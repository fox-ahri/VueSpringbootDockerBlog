package com.ahriknow.blog.service;

import com.ahriknow.blog.dao.TagDao;
import com.ahriknow.blog.entity.Tag;
import com.ahriknow.blog.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 查询标签数
     *
     * @return 标签数
     */
    public long count() {
        return tagDao.count();
    }

    /**
     * 根据 ids 查询标签
     *
     * @param ids 标签 ids
     * @return 标签
     */
    public Set<Tag> findByIds(List<String> ids) {
        return new HashSet<>(tagDao.findAllById(ids));
    }

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    /**
     * 保存新标签
     *
     * @param tag 新标签
     */
    public void insert(Tag tag) {
        tag.setId(idGenerator.nextId());
        tagDao.save(tag);
    }

    /**
     * 根据 id 删除标签
     *
     * @param id 标签 id
     */
    public void delete(String id) {
        tagDao.deleteById(id);
    }
}
