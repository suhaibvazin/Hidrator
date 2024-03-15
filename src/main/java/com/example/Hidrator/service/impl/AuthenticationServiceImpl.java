package com.example.Hidrator.service.impl;

import com.example.Hidrator.config.JwtService;
import com.example.Hidrator.dto.AuthenticationDTO;
import com.example.Hidrator.dto.AuthenticationResponse;
import com.example.Hidrator.entity.Token;
import com.example.Hidrator.entity.User;
import com.example.Hidrator.repository.TokenRepository;
import com.example.Hidrator.repository.UserRepository;
import com.example.Hidrator.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(AuthenticationDTO request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            return new AuthenticationResponse("null","User already exist");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        String jwtToken =jwtService.generateToken(savedUser);

        //save the token
        saveUserToken(jwtToken,savedUser);

        return new AuthenticationResponse(jwtToken,"User Registration successful");

    }

    public AuthenticationResponse authenticateUser(AuthenticationDTO request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        User returnedUser=userRepository.findByUsername(request.getUsername()).orElseThrow();
        //generate token

       String jwtToken= jwtService.generateToken(returnedUser);
        //revoke all token
        revokeAllToken(returnedUser);
        //save the user token
        saveUserToken(jwtToken,returnedUser);

        return (new AuthenticationResponse(jwtToken,"user login was successful"));
    }

    private void revokeAllToken(User returnedUser) {
        List<Token> tokens = tokenRepository.findAllTokenByUser(returnedUser.getId());

        if(tokens.isEmpty()){
            return;
        }
        tokens.forEach(t->t.setIsLoggedOut(true));
        tokenRepository.saveAll(tokens);
    }

    private void saveUserToken(String jwtToken, User savedUser) {
        Token token = Token.builder()
                .token(jwtToken)
                .isLoggedOut(false)
                .user(savedUser)
                .build();
        tokenRepository.save(token);
    }

}
