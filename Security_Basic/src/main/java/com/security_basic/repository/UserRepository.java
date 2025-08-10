package com.security_basic.repository;

import com.security_basic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

   boolean existsUserByUsername(String username);

   User findUserByUsername(String username);
}
