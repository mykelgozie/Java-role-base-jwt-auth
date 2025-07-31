package org.example.jwtauth.Service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    public String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
