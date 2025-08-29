package com.bajajfinserv.javachallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookResponse {
    
    @JsonProperty("webhook")
    private String webhook;
    
    @JsonProperty("accessToken")
    private String accessToken;
    
    public WebhookResponse() {}
    
    public WebhookResponse(String webhook, String accessToken) {
        this.webhook = webhook;
        this.accessToken = accessToken;
    }
    
    // Getters and Setters
    public String getWebhook() {
        return webhook;
    }
    
    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}