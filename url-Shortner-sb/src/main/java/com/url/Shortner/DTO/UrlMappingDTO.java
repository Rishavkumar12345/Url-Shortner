package com.url.Shortner.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UrlMappingDTO {
    private long id;
    private String originalUrl;
    private String shortUrl;
    private int clickCount;
    private LocalDateTime createdDate;
    private String userName;
}
