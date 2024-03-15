package com.example.Hidrator.service;

import com.example.Hidrator.dto.AuthenticationDTO;
import com.example.Hidrator.dto.AuthenticationResponse;

public interface AuthenticationService {
    public AuthenticationResponse authenticateUser(AuthenticationDTO request);
    public AuthenticationResponse registerUser(AuthenticationDTO request);
}
