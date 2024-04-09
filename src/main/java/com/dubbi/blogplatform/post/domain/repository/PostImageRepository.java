package com.dubbi.blogplatform.post.domain.repository;

import com.dubbi.blogplatform.post.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
}
