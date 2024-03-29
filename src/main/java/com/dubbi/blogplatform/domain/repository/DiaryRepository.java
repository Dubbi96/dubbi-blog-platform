package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary,Long> {
}
