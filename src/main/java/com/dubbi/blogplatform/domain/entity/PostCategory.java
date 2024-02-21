package com.dubbi.blogplatform.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST_CATEGORY")
@AllArgsConstructor
public class PostCategory {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @JoinColumn(name = "PARENT_ID")
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private PostCategory parentCategory;

}