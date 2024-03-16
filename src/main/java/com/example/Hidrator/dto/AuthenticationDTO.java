package com.example.Hidrator.dto;


import com.example.Hidrator.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthenticationDTO {
    @NotBlank(message = "firstname cant be empty")
    private String firstName;
    @NotBlank(message = "lastname cant be empty")
    private String lastName;
    @NotBlank(message = "username cant be empty")
    private String username;
    @NotBlank(message = "password cant be empty")
    @Size(min=8, max=25)
    private String password;
    @NotBlank(message = "role cant be empty")
    private Role role;
    private String token;
}
