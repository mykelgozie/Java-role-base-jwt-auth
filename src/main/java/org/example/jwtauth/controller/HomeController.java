package org.example.jwtauth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class HomeController {

    @GetMapping()
    public String GetHome(){

        return "Hello World!";
    }
}
