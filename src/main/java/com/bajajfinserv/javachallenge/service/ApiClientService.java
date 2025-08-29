package com.bajajfinserv.javachallenge.service;

import com.bajajfinserv.javachallenge.dto.SolutionRequest;
import com.bajajfinserv.javachallenge.dto.WebhookRequest;
import com.bajajfinserv.javachallenge.dto.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiClientService {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiClientService.class);
    
    @Value("${api.base.url}")
    private String baseUrl;
    
    @Value("${api.generate.webhook}")
    private String generateWebhookEndpoint;
    
    @Value("${api.test.webhook}")
    private String testWebhookEndpoint;
    
    private final RestTemplate restTemplate;
    
    public ApiClientService() {
        this.restTemplate = new RestTemplate();
    }
    
    public WebhookResponse generateWebhook() {
        try {
            String url = baseUrl + generateWebhookEndpoint;
            logger.info("Sending POST request to generate webhook: {}", url);
            
            // Create request body with your details
            WebhookRequest request = new WebhookRequest("John Doe", "REG12347", "john@example.com");
            
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Create HTTP entity
            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
            
            // Send POST request
            ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                WebhookResponse.class
            );
            
            WebhookResponse webhookResponse = response.getBody();
            logger.info("Successfully received webhook response");
            logger.info("Webhook URL: {}", webhookResponse.getWebhook());
            logger.info("Access Token received: {}", 
                webhookResponse.getAccessToken() != null ? "Yes" : "No");
            
            return webhookResponse;
            
        } catch (Exception e) {
            logger.error("Error generating webhook: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate webhook", e);
        }
    }
    
    public void submitSolution(String finalQuery, String accessToken) {
        try {
            String url = baseUrl + testWebhookEndpoint;
            logger.info("Submitting solution to: {}", url);
            
            // Create request body
            SolutionRequest request = new SolutionRequest(finalQuery);
            
            // Set headers with JWT token
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);
            
            // Create HTTP entity
            HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);
            
            // Send POST request
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
            );
            
            logger.info("Solution submitted successfully!");
            logger.info("Response status: {}", response.getStatusCode());
            logger.info("Response body: {}", response.getBody());
            
        } catch (Exception e) {
            logger.error("Error submitting solution: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to submit solution", e);
        }
    }
}