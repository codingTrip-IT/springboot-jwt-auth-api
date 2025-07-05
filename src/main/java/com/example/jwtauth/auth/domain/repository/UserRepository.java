package com.example.jwtauth.auth.domain.repository;

import com.example.jwtauth.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);
}
