package com.ahriknow.blog.dao;

import com.ahriknow.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagDao extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {
}
