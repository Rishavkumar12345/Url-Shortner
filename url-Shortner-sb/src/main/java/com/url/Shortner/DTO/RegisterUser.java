package com.url.Shortner.DTO;

import java.util.Set;

import lombok.Data;

@Data
public class RegisterUser {
    private String username;
    private String email;
    private Set<String>roles;

    private String password;
}
