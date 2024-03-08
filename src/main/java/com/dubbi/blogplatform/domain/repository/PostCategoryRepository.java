package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory,Long> {
}
