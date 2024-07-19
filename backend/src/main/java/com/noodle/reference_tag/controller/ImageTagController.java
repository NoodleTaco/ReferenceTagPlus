package com.noodle.reference_tag.controller;

import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.dto.ImageTagDto;
import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.service.ImageTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image-tags")
public class ImageTagController {
    private final ImageTagService imageTagService;

    public ImageTagController(ImageTagService imageTagService) {
        this.imageTagService = imageTagService;
    }

    @PostMapping
    public ResponseEntity<ImageTagDto> associateTagWithImage(@RequestParam("imageId") Long imageId, @RequestParam("tagId") Long tagId) {
        ImageTagDto imageTagDto = imageTagService.associateTagWithImage(imageId, tagId);
        return ResponseEntity.ok(imageTagDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeTagFromImage(@RequestParam("imageId") Long imageId, @RequestParam("tagId") Long tagId) {
        imageTagService.removeTagFromImage(imageId, tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image/{imageId}/tags")
    public ResponseEntity<List<TagDto>> getTagsForImage(@PathVariable("imageId") Long imageId) {
        List<TagDto> tags = imageTagService.getTagsForImage(imageId);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ImageDto>> findImagesByTags(@RequestParam("tagIds") List<Long> tagIds) {
        List<ImageDto> images = imageTagService.findImageBySearchedTags(tagIds);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/find")
    public ResponseEntity<List<ImageTagDto>> findByImageIdAndTagId(
            @RequestParam("imageId") Long imageId,
            @RequestParam("tagId") Long tagId) {
        List<ImageTagDto> imageTags = imageTagService.findByImageIdAndTagId(imageId, tagId);
        return ResponseEntity.ok(imageTags);
    }
}