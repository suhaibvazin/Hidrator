package com.example.Hidrator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Boolean isLoggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
