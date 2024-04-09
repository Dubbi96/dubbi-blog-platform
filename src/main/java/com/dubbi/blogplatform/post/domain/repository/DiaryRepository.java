package com.dubbi.blogplatform.post.domain.repository;

import com.dubbi.blogplatform.post.domain.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary,Long> {
}
