package com.dubbi.blogplatform.oneliner.domain.entity;

import com.dubbi.blogplatform.imageserver.domain.entity.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oneliner_image")
public class OnelinerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "oneliner_id", nullable = false, updatable = false)
    private Oneliner oneliner;

    @Column(name = "sequence")
    private Long sequence;
}