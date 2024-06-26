package com.dubbi.blogplatform.post.domain.entity;

import com.dubbi.blogplatform.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "post_category")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class PostCategory extends BaseEntity {

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "child_id") // 변경: 부모 카테고리가 없을 수도 있으므로 nullable = true
    private PostCategory child;

}
