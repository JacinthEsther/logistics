package com.example.logistics.repository;

import com.example.logistics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmailIgnoreCase(String email);

}
