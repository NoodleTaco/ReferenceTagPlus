package com.noodle.reference_tag.service;

import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.domain.entity.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagService {

    TagDto save(TagDto tagDto);

    Optional<TagDto> findTagById(Long id);

    List<TagDto> findAllTags();

    boolean isExists(Long id);

    void deleteTagById(Long id);

    Optional<TagDto> findByName(String name);
}

