package com.ahriknow.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Category {

    @Id
    private String id;
    private String name;
    private String describe;
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.CreationTimestamp
    @Column(updatable = false)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.UpdateTimestamp
    private Date updateDate;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();

    @Transient
    private Integer count;
}
