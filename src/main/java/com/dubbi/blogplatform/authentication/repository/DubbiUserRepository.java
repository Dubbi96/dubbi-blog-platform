package com.dubbi.blogplatform.authentication.repository;

import com.dubbi.blogplatform.authentication.entity.DubbiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DubbiUserRepository extends JpaRepository<DubbiUser, Long> {
    Optional<DubbiUser> findByUserNameAndProvider(String username, String provider);

    Optional<DubbiUser> findByUserName(String username);
}
