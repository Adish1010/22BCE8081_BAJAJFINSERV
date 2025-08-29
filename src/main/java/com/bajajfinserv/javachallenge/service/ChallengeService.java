package com.bajajfinserv.javachallenge.service;

import com.bajajfinserv.javachallenge.dto.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ChallengeService implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(ChallengeService.class);
    
    private final ApiClientService apiClientService;
    private final SqlSolutionService sqlSolutionService;
    
    @Autowired
    public ChallengeService(ApiClientService apiClientService, SqlSolutionService sqlSolutionService) {
        this.apiClientService = apiClientService;
        this.sqlSolutionService = sqlSolutionService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting Bajaj Finserv Java Challenge...");
        
        try {
            // Step 1: Generate webhook
            logger.info("Step 1: Generating webhook...");
            WebhookResponse webhookResponse = apiClientService.generateWebhook();
            
            if (webhookResponse == null || webhookResponse.getWebhook() == null 
                || webhookResponse.getAccessToken() == null) {
                logger.error("Invalid webhook response received");
                return;
            }
            
            // Step 2: Generate SQL solution
            logger.info("Step 2: Generating SQL solution...");
            String sqlSolution = sqlSolutionService.generateSqlSolution();
            logger.info("SQL Solution generated: {}", sqlSolution);
            
            // Print explanation
            logger.info("Solution explanation: {}", sqlSolutionService.getSolutionExplanation());
            
            // Step 3: Submit solution
            logger.info("Step 3: Submitting solution...");
            apiClientService.submitSolution(sqlSolution, webhookResponse.getAccessToken());
            
            logger.info("Challenge completed successfully!");
            
        } catch (Exception e) {
            logger.error("Challenge execution failed: {}", e.getMessage(), e);
        }
    }
}