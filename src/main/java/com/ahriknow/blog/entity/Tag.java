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
public class Tag {

    @Id
    private String id;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.CreationTimestamp
    @Column(updatable = false)
    private Date createDate;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();
}
