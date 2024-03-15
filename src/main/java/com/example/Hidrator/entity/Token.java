package com.example.Hidrator.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    @Column(name = "logged_out")
    private Boolean loggedout;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
