package com.noodle.reference_tag.service;

import com.noodle.reference_tag.domain.entity.ImageEntity;
import com.noodle.reference_tag.domain.entity.ImageTagEntity;
import com.noodle.reference_tag.domain.entity.TagEntity;

import java.util.List;

public interface ImageTagService {

    ImageTagEntity associateTagWithImage(Long imageId, Long tagId);

    void removeTagFromImage(Long imageId, Long tagId);

    List<TagEntity> getTagsForImage(Long imageId);

    List<ImageTagEntity> findByImageIdAndTagId(Long imageId, Long tagId);

    void deleteByImageId(Long imageId);

    void deleteByTagId(Long tagId);

    List<ImageEntity> findImageBySearchedTags(List<Long> tagIds);


}
