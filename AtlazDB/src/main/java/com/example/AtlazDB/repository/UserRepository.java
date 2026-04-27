package com.example.AtlazDB.repository;

import com.example.AtlazDB.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRegistration(String registration);
}
