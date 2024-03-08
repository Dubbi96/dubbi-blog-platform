package com.dubbi.blogplatform.domain.repository;

import com.dubbi.blogplatform.domain.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Post p SET p.isDeactivated = true WHERE p.id = :id")
    int deactivatePostById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Post p WHERE p.isDeactivated = true AND p.id = :id")
    int deletePostById(@Param("id") Long id);
}
