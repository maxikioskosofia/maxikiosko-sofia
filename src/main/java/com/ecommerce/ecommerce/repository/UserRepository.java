package com.ecommerce.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.domain.enums.RoleEnumUser;


public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    long countByRole(RoleEnumUser role);
    boolean existsByName(String name);
}
