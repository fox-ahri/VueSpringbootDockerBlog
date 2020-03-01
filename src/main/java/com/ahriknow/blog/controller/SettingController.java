package com.ahriknow.blog.controller;

import com.ahriknow.blog.entity.Setting;
import com.ahriknow.blog.entity.User;
import com.ahriknow.blog.service.SettingService;
import com.ahriknow.blog.utils.Auth;
import com.ahriknow.blog.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private Auth auth;

    @GetMapping(value = "/admin/setting/about")
    public Response getAbout(@RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        Setting setting = settingService.getAbout();
        return new Response(true, 200, "查询成功", setting);
    }

    @PostMapping(value = "/admin/setting/about")
    public Response postAbout(@RequestBody Setting setting, @RequestHeader("token") String token) {
        User user = auth.auth(token);
        if (user == null)
            return new Response(false, 0, "未登录!", null);
        settingService.postAbout(setting);
        return new Response(true, 200, "更新成功", null);
    }

    /*==================================================================================*/

    @GetMapping(value = "/index/setting/about")
    public Response about(){
        Setting setting = settingService.getAbout();
        return new Response(true, 200, "查询成功", setting);
    }
}
