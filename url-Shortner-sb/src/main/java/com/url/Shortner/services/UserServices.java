package com.url.Shortner.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.url.Shortner.DTO.LoginUser;
import com.url.Shortner.models.User;
import com.url.Shortner.repository.UserRepository;
import com.url.Shortner.securityJwt.JwtAuthenticationResponse;
import com.url.Shortner.securityJwt.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServices {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public User registerUser(User user){
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse authenticateUser(LoginUser loginuser){

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginuser.getUsername(),loginuser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpls userDetails=(UserDetailsImpls) authentication.getPrincipal();
        String jwtToken=jwtUtils.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwtToken);
    }

    public User findByUsername(String name) {
        return userRepository.findByUserName(name).orElseThrow(
            ()-> new UsernameNotFoundException("user not found with username"+name)
        );
    }
}
