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
@Table(name = "oneliner")
@EntityListeners(AuditingEntityListener.class)
public class Oneliner extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private User creator;

    @Column(name = "content")
    private String content;
}