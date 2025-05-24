package com.url.Shortner.securityJwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtils jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    // This method takes token from request header and validate it and set the authentication in the security context

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        try{
            // Extract the Jwt token from the request header
            String jwt=jwtTokenProvider.getJwtFromHeader(request);


            // Validate the token
            if(jwt!=null && jwtTokenProvider.validateToken(jwt)){
                // If the token is valid, set the authentication in the security context
                // This allows Spring Security to recognize the user as authenticated
                String username=jwtTokenProvider.getUsernameFromToken(jwt);

                // Load the user details using the username
                UserDetails userDetails=userDetailsService.loadUserByUsername(username);

                if(userDetails!=null){
                    // Create an authentication object using the user details
                    UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    
                    // Set the authentication details (optional)
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Set the authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       
        filterChain.doFilter(request,response);

    }
    
     
}
