package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.Comment;
import com.dubbi.blogplatform.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
