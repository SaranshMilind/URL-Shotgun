
package com.saransh.urlshortener.service;
import com.saransh.urlshortener.exception.UrlNotFoundException;
import java.util.Optional;
import com.saransh.urlshortener.entity.ClickAnalytics;
import com.saransh.urlshortener.repository.ClickAnalyticsRepository;
import jakarta.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

import com.saransh.urlshortener.entity.Url;
import com.saransh.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private ClickAnalyticsRepository clickAnalyticsRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    void shouldReturnOriginalUrlAndRecordClick() {
        Url url = new Url();
        url.setOriginalUrl("https://example.com");
        url.setShortCode("abc123");
        url.setClickCount(0L);

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(urlRepository.findByShortCode("abc123"))
                .thenReturn(Optional.of(url));
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(request.getHeader("User-Agent")).thenReturn("JUnit Test");
        when(request.getHeader("Referer")).thenReturn("https://google.com");

        String result = urlService.getOriginalUrl("abc123", request);

        assertEquals("https://example.com", result);
        assertEquals(1, url.getClickCount());

        verify(clickAnalyticsRepository).save(any(ClickAnalytics.class));
        verify(urlRepository).save(url);
    }

    @Test
    void shouldThrowExceptionWhenRedirectCodeDoesNotExist() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(urlRepository.findByShortCode("missing"))
                .thenReturn(Optional.empty());

        assertThrows(
                UrlNotFoundException.class,
                () -> urlService.getOriginalUrl("missing", request)
        );

        verify(clickAnalyticsRepository, never())
                .save(any(ClickAnalytics.class));
    }


    @Test
    void shouldCreateShortUrl() {

        String originalUrl = "https://example.com";

        when(urlRepository.save(any(Url.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Url result = urlService.createShortUrl(originalUrl);

        assertNotNull(result);
        assertEquals(originalUrl, result.getOriginalUrl());
        assertNotNull(result.getShortCode());
        assertEquals(6, result.getShortCode().length());

        verify(urlRepository).save(any(Url.class));

        verify(urlRepository, never())
                .existsByShortCode(anyString());
    }

    @Test
    void shouldGenerateShortCodeContainingOnlyLettersAndDigits() {
        when(urlRepository.save(any(Url.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Url result = urlService.createShortUrl("https://example.com");

        assertTrue(result.getShortCode().matches("[A-Za-z0-9]{6}"));
    }

    @Test
    void shouldSaveUrlWithCorrectOriginalUrl() {
        String originalUrl = "https://example.org/test";

        when(urlRepository.save(any(Url.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        urlService.createShortUrl(originalUrl);

        ArgumentCaptor<Url> captor = ArgumentCaptor.forClass(Url.class);
        verify(urlRepository).save(captor.capture());

        Url savedUrl = captor.getValue();

        assertEquals(originalUrl, savedUrl.getOriginalUrl());
        assertTrue(savedUrl.getShortCode().matches("[A-Za-z0-9]{6}"));
    }

    @Test
    void shouldThrowExceptionWhenShortCodeDoesNotExist() {
        String shortCode = "abc123";

        when(urlRepository.findByShortCode(shortCode))
                .thenReturn(Optional.empty());

        assertThrows(
                UrlNotFoundException.class,
                () -> urlService.getAnalytics(shortCode)
        );

        verify(urlRepository).findByShortCode(shortCode);
    }


}
