package com.example.Hidrator.config;

import com.example.Hidrator.entity.User;
import com.example.Hidrator.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
@RequiredArgsConstructor
@Service
public class JwtService {

    private final TokenRepository tokenRepository;

    String SECRET_KEY = "afytuh123456hhijsknmko890fdfygiuijjoj2345kjjhggh";

    public String generateToken(User user){
       String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*24*60*60*1000))
                .signWith(getSigningKey())
                .compact();
       return token;
    }

    public boolean isValid(String token, UserDetails user){
        String username= extractUserName(token);
        boolean isTokenValid=tokenRepository.findByToken(token)
                .map(t ->!t.getIsLoggedOut())
                .orElse(false);
       return username.equals(user.getUsername()) && isTokenValid && isTokenExpired(token);
    }
    public String extractUserName (String token) {
        return extractClaim (token,Claims::getSubject);
    }

    private boolean isTokenExpired(String token){
       return extractExpiry(token).before(new Date());
    }

    private Date extractExpiry(String token){
        return extractClaim (token,Claims::getExpiration);
    }



    private <T> T extractClaim (String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims (token);
        return resolver.apply(claims);

    }

    private Claims extractAllClaims (String token){

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims (token)
                .getPayload();
    }

    private SecretKey getSigningKey() {

        byte[] keyByte = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
