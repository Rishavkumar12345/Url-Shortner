package com.url.Shortner.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.url.Shortner.models.UrlMapping;
import com.url.Shortner.services.UrlMappingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UrlRedirectController {

    private UrlMappingService urlMappingService;
    
    @GetMapping("/{shorturl}")
    public ResponseEntity<Void>directurl(@PathVariable String shorturl){
        
        UrlMapping urlMapping=urlMappingService.getoriginalUrlFromShorturl(shorturl);
        if(urlMapping!=null){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", urlMapping.getOriginalUrl());
            return  ResponseEntity.status(302).headers(httpHeaders).build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
