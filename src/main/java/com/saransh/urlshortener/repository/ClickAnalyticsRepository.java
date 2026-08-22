package com.saransh.urlshortener.repository;

import com.saransh.urlshortener.entity.ClickAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClickAnalyticsRepository
        extends JpaRepository<ClickAnalytics, Long> {
}