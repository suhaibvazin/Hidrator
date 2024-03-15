package com.example.Hidrator.controller;

import com.example.Hidrator.dto.AuthenticationDTO;
import com.example.Hidrator.dto.AuthenticationResponse;
import com.example.Hidrator.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(authenticationService.registerUser(authenticationDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(authenticationService.authenticateUser(authenticationDTO));
    }
    @GetMapping ("/generateRestToken")
    public ResponseEntity<AuthenticationResponse> generateResetToken(@RequestBody AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(authenticationService.generateResetPasswordToken(authenticationDTO));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<AuthenticationResponse> resetPassword(@RequestBody AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(authenticationService.resetPassword(authenticationDTO));
    }


}
