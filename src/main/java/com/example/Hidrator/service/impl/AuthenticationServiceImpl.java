package com.example.Hidrator.service.impl;

import com.example.Hidrator.config.JwtService;
import com.example.Hidrator.dto.AuthenticationDTO;
import com.example.Hidrator.dto.AuthenticationResponse;
import com.example.Hidrator.entity.Token;
import com.example.Hidrator.entity.User;
import com.example.Hidrator.exception.AuthException;
import com.example.Hidrator.repository.TokenRepository;
import com.example.Hidrator.repository.UserRepository;
import com.example.Hidrator.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(AuthenticationDTO request) throws AuthException {
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new AuthException("User already exist");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        String jwtToken =jwtService.generateToken(savedUser,false);

        //save the token
        saveUserToken(jwtToken,savedUser);

        return new AuthenticationResponse(jwtToken,"User Registration successful");

    }


    public AuthenticationResponse authenticateUser(AuthenticationDTO request) throws AuthException {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        }catch(Exception e){
            throw new AuthException("error occured during authentication");
        }

        User returnedUser=userRepository.findByUsername(request.getUsername()).orElseThrow();
        //generate token

       String jwtToken= jwtService.generateToken(returnedUser,false);
        //revoke all token
        revokeAllToken(returnedUser);
        //save the user token
        saveUserToken(jwtToken,returnedUser);

        return (new AuthenticationResponse(jwtToken,"user login was successful"));
    }

    public AuthenticationResponse generateResetPasswordToken(AuthenticationDTO request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        //generate password reset token
        String token = jwtService.generateToken(user,true);
        Token tokenObj = Token.builder()
                .user(user)
                .token(token)
                .build();
        tokenRepository.save(tokenObj);
        return (new AuthenticationResponse(token,"password reset token generated"));
    }

    public AuthenticationResponse resetPassword(AuthenticationDTO request) {
        String token= request.getToken();
        User user = userRepository.findByToken(token).orElseThrow();

        if(jwtService.isValid(token,user)){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
        }
        Token deteleToken = tokenRepository.findByToken(token).orElseThrow();
        tokenRepository.delete(deteleToken);
        return new AuthenticationResponse(null,"password reset successful");
    }

    public AuthenticationResponse logout(AuthenticationDTO request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        //revoking all token
        revokeAllToken(user);
        //clearing security context
        SecurityContextHolder.clearContext();
        return new AuthenticationResponse(null,"successfully logged out");
    }



    private void revokeAllToken(User returnedUser) {
        List<Token> tokens = tokenRepository.findAllTokenByUser(returnedUser.getId());

        if(tokens.isEmpty()){
            return;
        }
        tokens.forEach(t->t.setLoggedout(true));
        tokenRepository.saveAll(tokens);
    }

    private void saveUserToken(String jwtToken, User savedUser) {
        Token token = Token.builder()
                .token(jwtToken)
                .loggedout(false)
                .user(savedUser)
                .build();
        tokenRepository.save(token);
    }

}
