package com.noodle.reference_tag.service;

import com.noodle.reference_tag.dto.ImageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageFrontendService {
    private final ApiService apiService;

    public ImageFrontendService(ApiService apiService) {
        this.apiService = apiService;
    }

    public ImageDto createImage(ImageDto imageDto) {
        return apiService.post("/images", imageDto, ImageDto.class);
    }

    public Optional<ImageDto> getImage(Long id) {
        return apiService.get("/images/" + id, ImageDto.class);
    }

    public List<ImageDto> getAllImages() {
        return apiService.getList("/images", ImageDto[].class);
    }

    public void deleteImage(Long id) {
        apiService.delete("/images/" + id);
    }

    public Optional<ImageDto> getImageByPath(String path) {
        return apiService.get("/images/path?path=" + path, ImageDto.class);
    }
}