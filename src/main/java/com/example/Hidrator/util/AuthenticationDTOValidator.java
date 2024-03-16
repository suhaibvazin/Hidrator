package com.example.Hidrator.util;

import com.example.Hidrator.dto.AuthenticationDTO;
import com.example.Hidrator.exception.AuthValidationException;


public class AuthenticationDTOValidator {
    public static void validate(AuthenticationDTO authenticationDTO) throws AuthValidationException {
        StringBuilder errorMessage = new StringBuilder();

        // Validate firstName
        if (authenticationDTO.getFirstName() == null || authenticationDTO.getFirstName().isEmpty()) {
            errorMessage.append("First name cannot be empty. ");
        }

        // Validate lastName
        if (authenticationDTO.getLastName() == null || authenticationDTO.getLastName().isEmpty()) {
            errorMessage.append("Last name cannot be empty. ");
        }

        // Validate username
        if (authenticationDTO.getUsername() == null || authenticationDTO.getUsername().isEmpty()) {
            errorMessage.append("Username cannot be empty. ");
        }

        // Validate password
        if (authenticationDTO.getPassword() == null || authenticationDTO.getPassword().isEmpty()) {
            errorMessage.append("Password cannot be empty. ");
        } else if (authenticationDTO.getPassword().length() < 8 || authenticationDTO.getPassword().length() > 25) {
            errorMessage.append("Password length must be between 8 and 25 characters. ");
        }

        // Validate role
        if (authenticationDTO.getRole() == null) {
            errorMessage.append("Role cannot be empty. ");
        }

        // If there are validation errors, throw ValidationException
        if (!errorMessage.isEmpty()) {
            throw new AuthValidationException("Validation failed: " + errorMessage);
        }
    }
}


