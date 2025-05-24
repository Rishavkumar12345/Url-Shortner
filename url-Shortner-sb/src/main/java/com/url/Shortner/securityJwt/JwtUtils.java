package com.url.Shortner.securityJwt;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.url.Shortner.services.UserDetailsImpls;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirations;


    public String getJwtFromHeader(HttpServletRequest request){
        String authHeader=request.getHeader("Authorization");

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }

    // This method generates a JWT token using the username and roles of the user
    public String generateToken(UserDetailsImpls userDetails){

        String username=userDetails.getUsername();

        // Convert the roles to a comma-separated string
        // This is useful if you want to store multiple roles in a single claim
        // For example, if the user has roles "ROLE_USER" and "ROLE_ADMIN", it will be stored as "ROLE_USER,ROLE_ADMIN"
        // This is done using Java Streams to collect the roles into a single string
        // The map method extracts the authority string from each GrantedAuthority object
        // The collect method joins them with a comma
        String roles=userDetails.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.joining(","));


        // Create the JWT token using the Jwts builder
        // The builder allows you to set various claims, such as the subject (username), custom data (roles), issued date, and expiration date
        return Jwts.builder()
                    .subject(username)                       // Set the subject (username)
                    .claim("roles", roles)                   // Add custom data ("roles")
                    .issuedAt(new Date())                    // Set when the token is created
                    .expiration(new Date((new Date().getTime() + jwtExpirations)))  // Set expiry time
                    .signWith(key())                         // Sign the token using secret key
                    .compact();                              // Generate the final token string
    }

    // This method is used to extract the username from the JWT token

    public String getUsernameFromToken(String token){
        return Jwts.parser()
                     .verifyWith((SecretKey)key())  // Verify the token using the secret key
                    .build().parseSignedClaims(token)  // Parse the token and extract claims
                    .getPayload().getSubject();  // Get the subject (username) from the claims
                    
    }


    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); // Decode the base64-encoded secret key
    }



    public boolean validateToken(String token){
        try{
            Jwts.parser()
                .verifyWith((SecretKey)key())
                .build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | io.jsonwebtoken.MalformedJwtException | 
                io.jsonwebtoken.ExpiredJwtException | io.jsonwebtoken.UnsupportedJwtException | 
                java.lang.IllegalArgumentException e) {
            return false;
        }
    }
}