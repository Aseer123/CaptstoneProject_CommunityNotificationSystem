package com.communityapp.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Value("${auth.service.url}")  // Add this to application.properties
    private String authServiceUrl;
    
    private final RestTemplate restTemplate;

    public UserService() {
        this.restTemplate = new RestTemplate();
    }

    public List<String> getAllResidentEmails() {
        try {
            logger.info("Fetching resident emails from auth service");
            String url = authServiceUrl + "/auth/users/role/RESIDENT/emails";
            ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
            );
            List<String> emails = response.getBody();
            logger.info("Successfully fetched {} resident emails", emails != null ? emails.size() : 0);
            return emails;
        } catch (Exception e) {
            logger.error("Failed to fetch resident emails: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch resident emails: " + e.getMessage());
        }
    }

//    public List<String> getAllResidentPhones() {
//        try {
//            logger.info("Fetching resident phone numbers from auth service");
//            String url = authServiceUrl + "/auth/users/role/RESIDENT/phones";
//            ResponseEntity<List<String>> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<String>>() {}
//            );
//            List<String> phones = response.getBody();
//            logger.info("Successfully fetched {} resident phone numbers", phones != null ? phones.size() : 0);
//            return phones;
//        } catch (Exception e) {
//            logger.error("Failed to fetch resident phone numbers: {}", e.getMessage(), e);
//            throw new RuntimeException("Failed to fetch resident phone numbers: " + e.getMessage());
//        }
//    }
    
}