package org.example.jwtauth.controller;

import org.example.jwtauth.Dto.JwtAuthenticationResponse;
import org.example.jwtauth.Dto.RefreshTokenRequest;
import org.example.jwtauth.Dto.SignInRequest;
import org.example.jwtauth.Dto.SignUpRequest;
import org.example.jwtauth.Entities.User;
import org.example.jwtauth.Service.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest ) {
        return  ResponseEntity.ok(authenticationService.SignUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest ) {
        return  ResponseEntity.ok(authenticationService.SignIn(signInRequest));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<JwtAuthenticationResponse> RefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest ) {
        return  ResponseEntity.ok(authenticationService.RefreshToken(refreshTokenRequest));
    }




}
