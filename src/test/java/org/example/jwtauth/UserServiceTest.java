package org.example.jwtauth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.example.jwtauth.Entities.User;
import org.example.jwtauth.Service.Impl.UserServiceImpl;
import org.example.jwtauth.Service.interfaces.UserService;
import org.example.jwtauth.repository.UserRepositoy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepositoy  userRepositoy;

   @InjectMocks
    private UserServiceImpl userService;



    @Test
    public void testGetUserByUsername(){
     var user = new User();
     user.setEmail("test@gmail.com");

        when(userRepositoy.findByEmail("test@gmail.com")).thenReturn(user);
        var result = userService.loadUserByUsername("test@gmail.com");

        var email = user.getEmail();
        var expected = "test@gmail.com";

      assertEquals(expected, result.getUsername());
    }


}
