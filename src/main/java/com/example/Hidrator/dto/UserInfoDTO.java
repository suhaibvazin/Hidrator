package com.example.Hidrator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private String firstName;
    private String lastName;
    private String username;
    private int interval;
    private int waterTarget;
}

