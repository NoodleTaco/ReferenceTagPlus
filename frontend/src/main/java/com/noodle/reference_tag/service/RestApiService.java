package com.noodle.reference_tag.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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
        ResponseEntity<T> response = restTemplate.getForEntity(baseUrl + endpoint, responseType);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public <T> List<T> getList(String endpoint, Class<T[]> responseType) {
        ResponseEntity<T[]> response = restTemplate.getForEntity(baseUrl + endpoint, responseType);
        return Arrays.asList(response.getBody());
    }

    @Override
    public void delete(String endpoint) {
        restTemplate.delete(baseUrl + endpoint);
    }
}
