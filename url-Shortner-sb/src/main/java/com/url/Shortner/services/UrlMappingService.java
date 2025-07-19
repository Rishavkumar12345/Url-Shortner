package com.url.Shortner.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.url.Shortner.DTO.ClickEventDTO;
import com.url.Shortner.DTO.UrlMappingDTO;
import com.url.Shortner.models.ClickEvent;
import com.url.Shortner.models.UrlMapping;
import com.url.Shortner.models.User;
import com.url.Shortner.repository.ClickEventRepository;
import com.url.Shortner.repository.UrlMappingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;

    public UrlMappingDTO CreateShorturl(String originalUrl,User user) {
        String shortUrl=generateShortUrl();
        UrlMapping urlMapping=new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return convertToDto(savedUrlMapping);
        
    }

    private UrlMappingDTO convertToDto(UrlMapping urlMapping){
        UrlMappingDTO urlMappingDTO=new UrlMappingDTO();
        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setUserName(urlMapping.getUser().getUserName());
        urlMappingDTO.setClickCount(urlMapping.getClickCount());
        urlMappingDTO.setCreatedDate(urlMapping.getCreatedDate());
        return urlMappingDTO;

    }

    private String generateShortUrl() {

        String character="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random=new Random();
        StringBuilder shorturl=new StringBuilder();

        for(int i=0;i<8;i++){
            shorturl.append(character.charAt(random.nextInt(character.length())));
        }
        return shorturl.toString();
    }

    public List<UrlMappingDTO>getUrlsByUser(User user){
        return urlMappingRepository.findByUser(user).stream().map(this :: convertToDto).toList();
    }

    /*public List<ClickEventDTO>getClickEventByDate(String shorturl,LocalDateTime start, LocalDateTime end){
        
        UrlMapping urlMapping=urlMappingRepository.findByShortUrl(shorturl);
        
        //System.out.println("Here is total details: "+urlMapping);

        if(urlMapping!=null){
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping,start,end).stream()
                                     .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                                     .entrySet().stream()
                                     .map(entry -> {
                                        ClickEventDTO clickEventDTO=new ClickEventDTO();
                                        clickEventDTO.setClickDate(entry.getKey());
                                        clickEventDTO.setCount(entry.getValue());
                                        return clickEventDTO;
                                     })
                                     .collect(Collectors.toList());
        }

        return null;

    }*/

    public List<ClickEventDTO> getClickEventByDate(String shorturl, LocalDateTime start, LocalDateTime end) {
    try {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shorturl);
        if (urlMapping != null) {
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        ClickEventDTO dto = new ClickEventDTO();
                        dto.setClickDate(entry.getKey());
                        dto.setCount(entry.getValue());
                        return dto;
                    })
                    .collect(Collectors.toList());
        } else {
            System.out.println("No URL mapping found for shorturl: " + shorturl);
        }
    } catch (Exception e) {
        e.printStackTrace(); // print the error
    }
    return Collections.emptyList();
}


    public Map<LocalDate, Long> getTotalClickByUser(User user, LocalDate start, LocalDate end) {
        List<UrlMapping>urlMappings=urlMappingRepository.findByUser(user);
        List<ClickEvent>clickEvent=clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings,start.atStartOfDay(),end.plusDays(1).atStartOfDay());

        return clickEvent.stream().collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));
    }

    public UrlMapping getoriginalUrlFromShorturl(String shorturl){
        UrlMapping urlMapping=urlMappingRepository.findByShortUrl(shorturl);

        if(urlMapping!=null){
            urlMapping.setClickCount(urlMapping.getClickCount()+1);
            urlMappingRepository.save(urlMapping);


            ClickEvent clickEvent=new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(urlMapping);
            clickEventRepository.save(clickEvent);
            
        }

        return urlMapping;
    }
    
}
