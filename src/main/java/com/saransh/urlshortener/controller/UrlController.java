package com.saransh.urlshortener.controller;

import com.saransh.urlshortener.entity.Url;
import com.saransh.urlshortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saransh.urlshortener.dto.CreateUrlRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<Url> createShortUrl(
            @Valid @RequestBody CreateUrlRequest request) {

        Url url = urlService.createShortUrl(request.getOriginalUrl());

        return ResponseEntity.ok(url);
    }
    @GetMapping("/{shortCode}/analytics")
    public ResponseEntity<Url> getAnalytics(
            @PathVariable String shortCode) {

        Url url = urlService.getAnalytics(shortCode);

        return ResponseEntity.ok(url);
    }
}