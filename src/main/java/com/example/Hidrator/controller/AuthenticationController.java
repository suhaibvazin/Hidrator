package com.example.Hidrator.controller;

import com.example.Hidrator.dto.AuthenticationDTO;
import com.example.Hidrator.dto.AuthenticationResponse;
import com.example.Hidrator.exception.AuthException;
import com.example.Hidrator.exception.AuthValidationException;
import com.example.Hidrator.repository.AuthRepository;
import com.example.Hidrator.service.AuthenticationService;
import com.example.Hidrator.util.AuthenticationDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @DeleteMapping("/delete/{userName}")
    @Transactional
    public ResponseEntity<AuthenticationResponse> delete(@PathVariable String userName){
        return ResponseEntity.ok(authenticationService.deleteUser(userName));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationDTO authenticationDTO) throws AuthException, AuthValidationException {
        AuthenticationDTOValidator.validate(authenticationDTO);
        return ResponseEntity.ok(authenticationService.registerUser(authenticationDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDTO authenticationDTO) throws AuthException, AuthValidationException {
        if (authenticationDTO.getUsername().isEmpty() || authenticationDTO.getPassword().isEmpty()){
            throw new AuthValidationException("Validation error: username or password cant be empty");
        }
        return ResponseEntity.ok(authenticationService.authenticateUser(authenticationDTO));
    }
    @GetMapping ("/generateRestToken")
    public ResponseEntity<AuthenticationResponse> generateResetToken(@RequestBody AuthenticationDTO authenticationDTO) throws AuthValidationException {
        if (authenticationDTO.getUsername().isEmpty()){
            throw new AuthValidationException("Username is empty");
        }
        return ResponseEntity.ok(authenticationService.generateResetPasswordToken(authenticationDTO));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<AuthenticationResponse> resetPassword(@RequestBody AuthenticationDTO authenticationDTO) throws AuthValidationException {
        if (authenticationDTO.getToken().isEmpty() || authenticationDTO.getPassword().isEmpty()){
            throw new AuthValidationException("Validation error:Token or password cant be empty");
        }
        return ResponseEntity.ok(authenticationService.resetPassword(authenticationDTO));
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<AuthenticationResponse> logout(@RequestBody AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(authenticationService.logout(authenticationDTO));
    }


}
