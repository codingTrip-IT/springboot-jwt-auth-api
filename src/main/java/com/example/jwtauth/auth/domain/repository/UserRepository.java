package com.example.jwtauth.auth.domain.repository;

import com.example.jwtauth.auth.domain.entity.User;
import com.example.jwtauth.auth.domain.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndRole(String username, UserRole role);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    boolean existsByUsername(String username);
}
