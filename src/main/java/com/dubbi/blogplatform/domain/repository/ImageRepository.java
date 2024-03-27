package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
