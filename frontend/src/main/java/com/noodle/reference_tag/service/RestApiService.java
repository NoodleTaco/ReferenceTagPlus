package com.noodle.reference_tag.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RestApiService implements ApiService{

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api"; // Adjust this to your backend URL

    public RestApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T post(String endpoint, Object body, Class<T> responseType) {
        return restTemplate.postForObject(baseUrl + endpoint, body, responseType);
    }

    @Override
    public <T> Optional<T> get(String endpoint, Class<T> responseType) {
        try {
            ResponseEntity<T> response = restTemplate.getForEntity(baseUrl + endpoint, responseType);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public <T> List<T> getList(String endpoint, Class<T[]> responseType) {
        try {
            ResponseEntity<T[]> response = restTemplate.getForEntity(baseUrl + endpoint, responseType);
            return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();
        } catch (HttpClientErrorException.NotFound e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void delete(String endpoint) {
        restTemplate.delete(baseUrl + endpoint);
    }
}
