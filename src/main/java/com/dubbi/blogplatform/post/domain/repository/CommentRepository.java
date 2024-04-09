package com.dubbi.blogplatform.post.domain.repository;

import com.dubbi.blogplatform.post.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
