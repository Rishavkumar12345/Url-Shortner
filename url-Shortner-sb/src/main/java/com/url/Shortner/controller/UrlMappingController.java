package com.url.Shortner.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.Shortner.DTO.ClickEventDTO;
import com.url.Shortner.DTO.UrlMappingDTO;
import com.url.Shortner.models.User;
import com.url.Shortner.services.UrlMappingService;
import com.url.Shortner.services.UserServices;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/url")
public class UrlMappingController {

    @Autowired
    private UserServices userServices;
    private UrlMappingService urlMappingService;
    
    @PostMapping("/shortner")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO>CreateShorturl(@RequestBody Map<String,String>request,Principal principal){
        
        String originalUrl=request.get("originalUrl");
        User user=userServices.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO=urlMappingService.CreateShorturl(originalUrl,user);
        return ResponseEntity.ok(urlMappingDTO);
    }

    @GetMapping("/allUrl")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDTO>>findallurl(Principal principal){
        
        User user=userServices.findByUsername(principal.getName());
        List<UrlMappingDTO>allurl=urlMappingService.getUrlsByUser(user);

        return ResponseEntity.ok(allurl);
    }

    @GetMapping("/clickeventanalystics/{shorturl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>>geturlAnalytics(@PathVariable String shorturl,@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){

        DateTimeFormatter datetimeformatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start=LocalDateTime.parse(startDate,datetimeformatter);
        LocalDateTime end=LocalDateTime.parse(endDate,datetimeformatter);

        List<ClickEventDTO>clickEventDTO=urlMappingService.getClickEventByDate(shorturl,start,end);
        return ResponseEntity.ok(clickEventDTO);
        
    }

    @GetMapping("/totalcount")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate,Long>>getTotalcount(Principal principal,@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){

        User user=userServices.findByUsername(principal.getName());
        DateTimeFormatter datetimeformatter=DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start=LocalDate.parse(startDate,datetimeformatter);
        LocalDate end=LocalDate.parse(endDate,datetimeformatter);

        Map<LocalDate,Long>counturlhit=urlMappingService.getTotalClickByUser(user,start,end);
        return ResponseEntity.ok(counturlhit);
        
    }
}
