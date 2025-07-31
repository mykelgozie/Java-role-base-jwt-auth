package org.example.jwtauth.Service.Impl;

import io.jsonwebtoken.Jwt;
import org.example.jwtauth.Dto.JwtAuthenticationResponse;
import org.example.jwtauth.Dto.RefreshTokenRequest;
import org.example.jwtauth.Dto.SignInRequest;
import org.example.jwtauth.Dto.SignUpRequest;
import org.example.jwtauth.Entities.Role;
import org.example.jwtauth.Entities.User;
import org.example.jwtauth.Service.interfaces.AuthenticationService;
import org.example.jwtauth.Service.interfaces.JWTService;
import org.example.jwtauth.repository.UserRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepositoy userRepositoy;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public User SignUp(SignUpRequest signUpRequest  ) {

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepositoy.save(user);
        return user;


    }

    public JwtAuthenticationResponse SignIn(SignInRequest signInRequest) {
         var authResponse = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
         if (!authResponse.isAuthenticated()) {
             return null;
         }
         var user = userRepositoy.findByEmail(signInRequest.getEmail());
         var jwt = jwtService.generateToken(user);
         var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

         JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
         jwtAuthenticationResponse.setToken(jwt);
         jwtAuthenticationResponse.setRefreshToken(refreshToken);

         return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse RefreshToken(RefreshTokenRequest refreshTokenRequest) {

       var userEmail =  jwtService.extractUsername(refreshTokenRequest.getToken());
       var user = userRepositoy.findByEmail(userEmail);
       if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
           var token = jwtService.generateToken(user);
           JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
           jwtAuthenticationResponse.setToken(token);
           jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
           return jwtAuthenticationResponse;
       }
      return null;
    }

}
