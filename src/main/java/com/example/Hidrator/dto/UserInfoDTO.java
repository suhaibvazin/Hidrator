package com.example.Hidrator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserInfoDTO {
    private String firstName;
    private String lastName;
    private String username;
    private Integer interval;
    private Integer waterTarget;
    private Integer serving;
    private Instant completeAt;
}

