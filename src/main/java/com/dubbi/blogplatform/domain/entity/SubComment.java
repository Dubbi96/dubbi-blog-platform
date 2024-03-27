package com.dubbi.blogplatform.domain.entity;

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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sub_comment")
@EntityListeners(AuditingEntityListener.class)
public class SubComment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(name = "text")
    private String text;
}