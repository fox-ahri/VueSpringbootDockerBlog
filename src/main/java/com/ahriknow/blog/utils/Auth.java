package com.ahriknow.blog.utils;

import com.ahriknow.blog.entity.Setting;
import com.ahriknow.blog.entity.User;
import com.ahriknow.blog.service.SettingService;
import com.ahriknow.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class Auth {

    @Autowired
    private SettingService settingService;

    @Autowired
    private UserService userService;

    /**
     * 验证用户是否登录
     * @param token token
     * @return 用户信息 或 null
     */
    public User auth(String token) {
        Setting setting = settingService.auth(token);
        if (setting != null && token.equals(setting.getContent()))
            return userService.findById(setting.getNote());
        return null;
    }
}
