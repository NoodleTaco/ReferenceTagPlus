package com.noodle.reference_tag.service;

import java.util.List;
import java.util.Optional;

public interface ApiService {
    <T> T post(String endpoint, Object body, Class<T> responseType);
    <T> Optional<T> get(String endpoint, Class<T> responseType);
    <T> List<T> getList(String endpoint, Class<T[]> responseType);
    void delete(String endpoint);
}
