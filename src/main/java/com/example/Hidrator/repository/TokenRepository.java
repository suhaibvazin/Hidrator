package com.example.Hidrator.repository;

import com.example.Hidrator.entity.Token;
import com.example.Hidrator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);
    @Query("SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id WHERE t.user.id = :userId AND t.isLoggedOut = false")
    List<Token> findAllTokenByUser(@Param("userId") Long userId);

}
