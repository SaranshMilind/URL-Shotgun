package com.saransh.urlshortener.service;

import com.saransh.urlshortener.entity.ClickAnalytics;
import com.saransh.urlshortener.entity.Url;
import com.saransh.urlshortener.exception.UrlNotFoundException;
import com.saransh.urlshortener.repository.ClickAnalyticsRepository;
import com.saransh.urlshortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final ClickAnalyticsRepository clickAnalyticsRepository;

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final Random random = new Random();

    public UrlService(
            UrlRepository urlRepository,
            ClickAnalyticsRepository clickAnalyticsRepository) {

        this.urlRepository = urlRepository;
        this.clickAnalyticsRepository = clickAnalyticsRepository;
    }

    public Url createShortUrl(String originalUrl) {

        String shortCode = generateShortCode();

        while (urlRepository.existsByShortCode(shortCode)) {
            shortCode = generateShortCode();
        }

        Url url = new Url();

        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);

        return urlRepository.save(url);
    }

    public String getOriginalUrl(
            String shortCode,
            HttpServletRequest request) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new UrlNotFoundException(
                                "Short URL not found: " + shortCode));

        ClickAnalytics clickAnalytics = new ClickAnalytics();

        clickAnalytics.setUrl(url);
        clickAnalytics.setIpAddress(request.getRemoteAddr());
        clickAnalytics.setUserAgent(request.getHeader("User-Agent"));
        clickAnalytics.setReferrer(request.getHeader("Referer"));
        clickAnalytics.setClickedAt(LocalDateTime.now());

        clickAnalyticsRepository.save(clickAnalytics);

        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    public Url getAnalytics(String shortCode) {

        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new UrlNotFoundException(
                                "Short URL not found: " + shortCode));
    }

    private String generateShortCode() {

        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }
}