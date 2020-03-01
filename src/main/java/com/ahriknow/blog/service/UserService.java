package com.ahriknow.blog.service;

import com.ahriknow.blog.dao.UserDao;
import com.ahriknow.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据 id 查询 user (验证用户登录)
     *
     * @param id 用户 id
     * @return 用户信息
     */
    public User findById(String id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * 登录查询
     *
     * @param user 用户信息
     * @return 查询到的用户
     */
    public List<User> login(User user) {
        return userDao.findAllByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    /**
     * 更新用户信息
     *
     * @param user 新的用户信息
     */
    public void update(User user) {
        userDao.save(user);
    }
}
