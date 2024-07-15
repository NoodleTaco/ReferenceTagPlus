package com.noodle.reference_tag.service;

import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.dto.ImageTagDto;
import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.domain.entity.ImageEntity;
import com.noodle.reference_tag.domain.entity.ImageTagEntity;
import com.noodle.reference_tag.domain.entity.TagEntity;

import java.util.List;

public interface ImageTagService {

    ImageTagDto associateTagWithImage(Long imageId, Long tagId);

    void removeTagFromImage(Long imageId, Long tagId);

    List<TagDto> getTagsForImage(Long imageId);

    List<ImageTagDto> findByImageIdAndTagId(Long imageId, Long tagId);

    void deleteByImageId(Long imageId);

    void deleteByTagId(Long tagId);

    List<ImageDto> findImageBySearchedTags(List<Long> tagIds);


}
