package com.travelsmart.blog_service.entity;

import com.travelsmart.blog_service.constant.BlogStatus;
import com.travelsmart.blog_service.utils.StringListConverter;
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
    @Convert(converter = StringListConverter.class)
    private List<String> tags;
    @Column
    private BlogStatus status;
    @OneToOne
    private BlogImageEntity image;
    @ManyToMany
    @JoinTable(
            name = "blog_category",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "category_name"))
    private Set<CategoryEntity> categories;

}
