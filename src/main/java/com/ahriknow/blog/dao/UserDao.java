package com.ahriknow.blog.dao;

import com.ahriknow.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    List<User> findAllByUsernameAndPassword(String username, String password);
}
