package com.saransh.urlshortener.service;

import com.saransh.urlshortener.entity.ClickAnalytics;
import com.saransh.urlshortener.repository.ClickAnalyticsRepository;
import com.saransh.urlshortener.repository.UrlRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Writes click analytics in the background so the redirect response
 * never waits on the database.
 */
@Component
public class ClickRecorder {

    private static final int MAX_LEN = 255;

    private final UrlRepository urlRepository;
    private final ClickAnalyticsRepository clickAnalyticsRepository;

    public ClickRecorder(UrlRepository urlRepository,
                         ClickAnalyticsRepository clickAnalyticsRepository) {
        this.urlRepository = urlRepository;
        this.clickAnalyticsRepository = clickAnalyticsRepository;
    }

    @Async("clickExecutor")
    @Transactional
    public void record(Long urlId, String ip, String userAgent,
                       String referrer, LocalDateTime clickedAt) {

        ClickAnalytics click = new ClickAnalytics();
        click.setUrl(urlRepository.getReferenceById(urlId));
        click.setIpAddress(truncate(ip));
        click.setUserAgent(truncate(userAgent));
        click.setReferrer(truncate(referrer));
        click.setClickedAt(clickedAt);

        clickAnalyticsRepository.save(click);

        // Atomic counter update: no read-modify-write, no lost updates.
        urlRepository.incrementClickCount(urlId);
    }

    private String truncate(String value) {
        if (value == null) {
            return null;
        }
        return value.length() <= MAX_LEN ? value : value.substring(0, MAX_LEN);
    }
}
