package com.travelsmart.blog_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "blog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String code;
    @Column
    private Date createDate;
    @Column
    private String userName;
    @Column
    private String userId;

    @Column
    private String content;
    @Column
    private List<String> tags;
    @ManyToMany
    @JoinTable(
            name = "blog_category",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "category_name"))
    private Set<CategoryEntity> categories;

}