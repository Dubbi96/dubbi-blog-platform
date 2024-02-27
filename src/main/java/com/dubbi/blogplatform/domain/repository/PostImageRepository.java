package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.Comment;
import com.dubbi.blogplatform.domain.entity.PostImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
}
