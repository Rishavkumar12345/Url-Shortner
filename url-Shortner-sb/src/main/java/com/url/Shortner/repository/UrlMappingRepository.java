package com.url.Shortner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.Shortner.models.UrlMapping;
import com.url.Shortner.models.User;

@Repository
public interface  UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    
    UrlMapping findByShortUrl(String shortUrl);
    List<UrlMapping>findByUser(User User);
}
