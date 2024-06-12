package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.itis.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}