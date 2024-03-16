package com.example.Hidrator.service;

import com.example.Hidrator.dto.AuthenticationDTO;
import com.example.Hidrator.dto.AuthenticationResponse;
import com.example.Hidrator.exception.AuthException;

public interface AuthenticationService {
     AuthenticationResponse authenticateUser(AuthenticationDTO request) throws AuthException;
     AuthenticationResponse registerUser(AuthenticationDTO request) throws AuthException;
     AuthenticationResponse resetPassword(AuthenticationDTO request);
     AuthenticationResponse generateResetPasswordToken(AuthenticationDTO request);
     AuthenticationResponse logout(AuthenticationDTO request);
}
