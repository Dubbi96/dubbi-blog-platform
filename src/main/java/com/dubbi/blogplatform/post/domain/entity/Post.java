package com.dubbi.blogplatform.post.domain.entity;

import com.dubbi.blogplatform.authentication.domain.entity.User;
import com.dubbi.blogplatform.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "post_category", nullable = false)
    private PostCategory postCategory;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private User creator;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_deactivated")
    private Boolean isDeactivated;

    @Column(name = "views")
    private Long views;

public void updateDetails(String title, String content) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
        if (content != null && !content.isEmpty()) {
            this.content = content;
        }
    }

}
