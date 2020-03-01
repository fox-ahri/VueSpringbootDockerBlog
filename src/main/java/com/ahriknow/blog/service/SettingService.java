package com.ahriknow.blog.service;

import com.ahriknow.blog.dao.SettingDao;
import com.ahriknow.blog.entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    @Autowired
    private SettingDao settingDao;

    /**
     * 查询 token
     *
     * @param token token
     * @return 是否存在 token
     */
    public Setting auth(String token) {
        return settingDao.findById("token").orElse(null);
    }

    /**
     * 设置新的 token
     *
     * @param token token
     * @param id    用户 id
     */
    public void set(String token, String id) {
        Setting setting = new Setting();
        setting.setId("token");
        setting.setContent(token);
        setting.setNote(id);
        settingDao.save(setting);
    }

    /**
     * 获取 about me 信息
     *
     * @return about me
     */
    public Setting getAbout() {
        return settingDao.findById("about").orElse(null);
    }

    /**
     * 设置新的 about me
     *
     * @param setting 新的 about me
     */
    public void postAbout(Setting setting) {
        settingDao.save(setting);
    }
}
