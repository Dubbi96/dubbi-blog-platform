package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
}
