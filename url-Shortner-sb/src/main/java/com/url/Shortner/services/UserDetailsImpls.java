package com.url.Shortner.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.url.Shortner.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsImpls implements UserDetails {

    private static final long serialVersionUID=1L;

    private String username;
    private String email;
    private long id;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpls(long id, String username, String email, String password,Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.email = email;
        this.id = id;
        this.password = password;
        this.username = username;
    }
    
    public static UserDetailsImpls build(User user){
        GrantedAuthority authority =new SimpleGrantedAuthority(user.getRole());
        return new UserDetailsImpls(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(authority)
          );
    }
    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
}
