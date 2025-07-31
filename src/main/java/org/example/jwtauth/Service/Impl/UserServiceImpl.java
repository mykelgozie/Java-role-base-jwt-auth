package org.example.jwtauth.Service.Impl;

import org.example.jwtauth.Service.interfaces.UserService;
import org.example.jwtauth.repository.UserRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoy userRepositoy;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositoy.findByEmail(username);
    }
}
