package com.ahriknow.blog.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Setting {

    @Id
    private String  id;
    private String note;
    private String content;
}
