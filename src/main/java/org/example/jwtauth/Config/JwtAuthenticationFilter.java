package org.example.jwtauth.Config;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.jwtauth.Service.interfaces.JWTService;
import org.example.jwtauth.Service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

          final String authorizationHeader = request.getHeader("Authorization");
          final String jwt;
          if(StringUtil.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
               filterChain.doFilter(request, response);
               return;
          }

          jwt = authorizationHeader.substring(7);
          var userName = jwtService.extractUsername(jwt);

          if(!StringUtil.isNullOrEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null){
              UserDetails userDetails = userService.loadUserByUsername(userName);

              if (jwtService.isTokenValid(jwt, userDetails)){

                  SecurityContext securityContext = SecurityContextHolder.getContext();
                  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  securityContext.setAuthentication(authentication);
                  SecurityContextHolder.setContext(securityContext);
              }
          }

          filterChain.doFilter(request, response);
    }
}
