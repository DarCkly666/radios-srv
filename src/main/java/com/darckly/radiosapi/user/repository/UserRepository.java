package com.darckly.radiosapi.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darckly.radiosapi.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
