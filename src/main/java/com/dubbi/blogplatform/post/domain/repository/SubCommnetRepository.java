package com.dubbi.blogplatform.post.domain.repository;

import com.dubbi.blogplatform.post.domain.entity.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCommnetRepository extends JpaRepository<SubComment,Long> {
}
