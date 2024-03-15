package com.example.Hidrator.dto;


import com.example.Hidrator.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AuthenticationDTO {


    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
}
