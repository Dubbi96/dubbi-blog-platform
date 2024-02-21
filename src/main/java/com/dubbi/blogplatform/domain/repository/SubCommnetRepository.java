package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCommnetRepository extends JpaRepository<SubComment,Long> {
}
