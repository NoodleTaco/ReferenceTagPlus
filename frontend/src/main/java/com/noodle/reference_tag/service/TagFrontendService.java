package com.noodle.reference_tag.service;

import com.noodle.reference_tag.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagFrontendService {
    private final ApiService apiService;

    public TagFrontendService(ApiService apiService) {
        this.apiService = apiService;
    }

    public TagDto createTag(TagDto tagDto) {
        return apiService.post("/tags", tagDto, TagDto.class);
    }

    public Optional<TagDto> getTag(Long id) {
        return apiService.get("/tags/" + id, TagDto.class);
    }

    public List<TagDto> getAllTags() {
        return apiService.getList("/tags", TagDto[].class);
    }

    public void deleteTag(Long id) {
        apiService.delete("/tags/" + id);
    }

    public Optional<TagDto> getTagByName(String name) {
        return apiService.get("/tags/name?name=" + name, TagDto.class);
    }
}