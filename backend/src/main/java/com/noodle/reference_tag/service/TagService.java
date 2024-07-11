package com.noodle.reference_tag.service;

import com.noodle.reference_tag.domain.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagService {

    TagEntity save(TagEntity tagEntity);

    Optional<TagEntity> findTagById(Long id);

    List<TagEntity> findAllTags();

    boolean isExists(Long id);

    void deleteTagById(Long id);

    Optional<TagEntity> findByName(String name);
}

