package com.ahriknow.blog.dao;

import com.ahriknow.blog.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingDao extends JpaRepository<Setting, String> {
}
