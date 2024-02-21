package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
