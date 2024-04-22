package com.dubbi.blogplatform.imageserver.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "image_prompt")
    private String imagePrompt;

    @Column(name = "content_type")
    private String contentType;
}
