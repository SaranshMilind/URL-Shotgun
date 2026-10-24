
package com.saransh.urlshortener.controller;

import com.saransh.urlshortener.exception.UrlNotFoundException;
import tools.jackson.databind.ObjectMapper;
import com.saransh.urlshortener.dto.CreateUrlRequest;
import com.saransh.urlshortener.entity.Url;
import com.saransh.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UrlService urlService;

    @Test
    void shouldReturn404WhenShortCodeDoesNotExist() throws Exception {
        org.mockito.Mockito.when(urlService.getAnalytics("invalid"))
                .thenThrow(new UrlNotFoundException("Short URL not found"));

        mockMvc.perform(get("/api/urls/invalid/analytics"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Short URL not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn400WhenOriginalUrlIsInvalid() throws Exception {
        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalUrl\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void shouldCreateShortUrl() throws Exception {
        String originalUrl = "https://example.com";

        Url savedUrl = new Url();
        savedUrl.setOriginalUrl(originalUrl);
        savedUrl.setShortCode("abc123");

        when(urlService.createShortUrl(originalUrl))
                .thenReturn(savedUrl);

        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl(originalUrl);

        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value(originalUrl))
                .andExpect(jsonPath("$.shortCode").value("abc123"));
    }
}
