package org.example.jwtauth.Service.interfaces;

import org.example.jwtauth.Dto.JwtAuthenticationResponse;
import org.example.jwtauth.Dto.RefreshTokenRequest;
import org.example.jwtauth.Dto.SignInRequest;
import org.example.jwtauth.Dto.SignUpRequest;
import org.example.jwtauth.Entities.User;

public interface AuthenticationService {

    User SignUp(SignUpRequest signUpRequest  );
    JwtAuthenticationResponse SignIn(SignInRequest signInRequest);
    JwtAuthenticationResponse RefreshToken(RefreshTokenRequest refreshTokenRequest);
}
