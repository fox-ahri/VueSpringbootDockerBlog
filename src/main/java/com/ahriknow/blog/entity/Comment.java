package com.ahriknow.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    private String id;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String nickname;
    private String email;
    private String avatar;
    private String website;
    private String pid;

    @ManyToOne(targetEntity = Article.class)
    @JsonIgnore
    private Article article;

    @Transient
    private String articleId;
}
