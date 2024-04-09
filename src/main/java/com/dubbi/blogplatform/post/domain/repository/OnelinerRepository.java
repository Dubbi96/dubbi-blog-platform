package com.dubbi.blogplatform.post.domain.repository;

import com.dubbi.blogplatform.post.domain.entity.Oneliner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnelinerRepository extends JpaRepository<Oneliner,Long> {
}
