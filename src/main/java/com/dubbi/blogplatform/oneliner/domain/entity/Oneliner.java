package com.dubbi.blogplatform.oneliner.domain.entity;

import com.dubbi.blogplatform.authentication.domain.entity.User;
import com.dubbi.blogplatform.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
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

    @OneToMany(mappedBy = "oneliner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OnelinerImage> onelinerImages;

}