package com.noodle.reference_tag.service;

import com.noodle.reference_tag.dto.ImageDto;
import com.noodle.reference_tag.dto.ImageTagDto;
import com.noodle.reference_tag.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageTagFrontendService {
    private final ApiService apiService;

    public ImageTagFrontendService(ApiService apiService) {
        this.apiService = apiService;
    }

    public ImageTagDto associateTagWithImage(Long imageId, Long tagId) {
        return apiService.post("/image-tags?imageId=" + imageId + "&tagId=" + tagId, null, ImageTagDto.class);
    }

    public void removeTagFromImage(Long imageId, Long tagId) {
        apiService.delete("/image-tags?imageId=" + imageId + "&tagId=" + tagId);
    }

    public List<TagDto> getTagsForImage(Long imageId) {
        return apiService.getList("/image-tags/image/" + imageId + "/tags", TagDto[].class);
    }

    public List<ImageDto> findImagesByTags(List<Long> tagIds) {
        String tagIdsParam = String.join(",", tagIds.stream().map(Object::toString).toArray(String[]::new));
        return apiService.getList("/image-tags/search?tagIds=" + tagIdsParam, ImageDto[].class);
    }
}