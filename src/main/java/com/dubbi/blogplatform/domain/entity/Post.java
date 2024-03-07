package com.dubbi.blogplatform.domain.entity;

import com.dubbi.blogplatform.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST")
@AllArgsConstructor
public class Post extends BaseEntity {

    @JoinColumn(name = "CREATOR")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "VIEWS")
    private int view;

    @Column(name = "IS_DEACTIVATED")
    private boolean is_deactivated;

    @JoinColumn(name = "POST_CATEGORY")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PostCategory postCategory;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<PostImage> postImage;

    public void updateDetails(String title, String content) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
        if (content != null && !content.isEmpty()) {
            this.content = content;
        }
    }

}
