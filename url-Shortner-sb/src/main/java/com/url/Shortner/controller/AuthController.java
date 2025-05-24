package com.url.Shortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.Shortner.DTO.LoginUser;
import com.url.Shortner.DTO.RegisterUser;
import com.url.Shortner.models.User;
import com.url.Shortner.services.UserServices;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserServices userService;

    @PostMapping("/public/signup")
    public ResponseEntity<?>registerUser(@RequestBody RegisterUser registerUser){
        
        User user=new User();
        user.setEmail(registerUser.getEmail());
        user.setUserName(registerUser.getUsername());
        user.setRole("ROLE_USER");
        user.setPassword(registerUser.getPassword());

        userService.registerUser(user);
        return ResponseEntity.ok("User Register Successfully");
    }

    @PostMapping(value = "/public/login", produces = "application/json")
    public ResponseEntity<?>loginUser(@RequestBody LoginUser loginUser){
        
        return ResponseEntity.ok(userService.authenticateUser(loginUser));
    }
    
}
