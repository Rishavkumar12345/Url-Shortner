package com.url.Shortner.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ClickEvent")

public class ClickEvent {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long Id;
    private LocalDateTime clickDate;

    @ManyToOne
    @JoinColumn(name="url_mapping_id")
    private UrlMapping urlMapping;

}
