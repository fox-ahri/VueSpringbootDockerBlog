package com.ahriknow.blog.controller;

import com.ahriknow.blog.entity.User;
import com.ahriknow.blog.service.SettingService;
import com.ahriknow.blog.service.UserService;
import com.ahriknow.blog.utils.Auth;
import com.ahriknow.blog.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private Auth auth;

    @PostMapping(value = "")
    public Response post(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().equals("") || user.getPassword() == null || user.getPassword().equals(""))
            return new Response(false, 0, "用户名或密码错误", null);
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        List<User> users = userService.login(user);
        if (users.size() > 0) {
            User u = users.get(0);
            String token = DigestUtils.md5DigestAsHex(u.getUsername().getBytes());
            settingService.set(token, u.getId());
            u.setPassword(null);
            return new Response(true, 200, token, u);
        }
        return new Response(false, 0, "用户名或密码错误", null);
    }

    @PutMapping(value = "")
    public Response put(@RequestBody User user, @RequestHeader("token") String token) {
        User u = auth.auth(token);
        if (u == null)
            return new Response(false, 0, "未登录!", null);
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if (!u.getPassword().equals(password))
            return new Response(false, 400, "原密码错误!", null);
        String newpass = DigestUtils.md5DigestAsHex(user.getPass1().getBytes());
        user.setPassword(newpass);
        userService.update(user);
        return new Response(true, 200, "更改成功！", null);
    }
}
