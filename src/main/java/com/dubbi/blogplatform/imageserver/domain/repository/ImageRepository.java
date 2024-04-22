package com.dubbi.blogplatform.imageserver.domain.repository;

import com.dubbi.blogplatform.imageserver.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findByUrl(String url);
}
