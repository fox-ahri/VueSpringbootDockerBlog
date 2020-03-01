package com.ahriknow.blog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Article {

    @Id
    private String id;
    private String title;  // 标题
    private String describe;  // 简介
    private String content;  // 内容
    private String type;  // 文章类型 (原创、翻译、转载)
    private String link;  // 引用链接
    private String image;  // 标题
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.CreationTimestamp
    @Column(updatable = false)
    private Date createDate;  // 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.UpdateTimestamp
    private Date updateDate;  // 更新时间
    @Column(columnDefinition = "false")
    private Boolean removed;  // 是否删除 (暂不可用)
    @Column(columnDefinition = "true")
    private Boolean commented;  // 允许评论
    @Column(columnDefinition = "false")
    private Boolean top;  // 置顶

    @ManyToOne(targetEntity = User.class)
    private User user;  // 用户

    @ManyToOne(targetEntity = Category.class)
    private Category category;  // 分类

    @ManyToMany(targetEntity = Tag.class)
    @JoinTable(name = "article_tags",
        joinColumns = {@JoinColumn(name = "articles_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "tags_id", referencedColumnName = "id")}
    )
    private Set<Tag> tags = new HashSet<>();  // 标签

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();  // 评论

    @Transient  // 不映射到数据库
    private String categoryId;  // 分类 id

    @Transient  // 不映射到数据库
    private List<String> tagIds;  // 标签 id 列表
}
