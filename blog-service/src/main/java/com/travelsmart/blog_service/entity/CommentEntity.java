package com.travelsmart.blog_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long parentId;
    @Column
    private String treeId;
    @Column
    private int nodeLeft;
    @Column
    private int nodeRight;
    @Column
    private String content;
    @Column
    private Date createDate;
    @Column
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    private BlogEntity blog;

}
