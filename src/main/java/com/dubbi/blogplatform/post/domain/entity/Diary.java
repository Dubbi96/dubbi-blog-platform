package com.dubbi.blogplatform.post.domain.entity;

import com.dubbi.blogplatform.authentication.domain.entity.User;
import com.dubbi.blogplatform.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diary")
@EntityListeners(AuditingEntityListener.class)
public class Diary extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private User creator;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;
}
