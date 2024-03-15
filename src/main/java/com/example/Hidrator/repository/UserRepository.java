package com.example.Hidrator.repository;

import com.example.Hidrator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u INNER JOIN Token t ON t.user.id = u.id WHERE t.token = :token")
    Optional<User>findByToken(String token);

}
