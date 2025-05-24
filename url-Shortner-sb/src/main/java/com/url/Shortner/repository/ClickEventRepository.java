package com.url.Shortner.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.Shortner.models.ClickEvent;
import com.url.Shortner.models.UrlMapping;

@Repository
public interface  ClickEventRepository extends JpaRepository<ClickEvent, Long>{

    List<ClickEvent>findByUrlMappingAndClickDateBetween(UrlMapping urlMapping,LocalDateTime start,LocalDateTime end);
    List<ClickEvent>findByUrlMappingInAndClickDateBetween(List<UrlMapping>urlMappings,LocalDateTime start,LocalDateTime end);
    
}
