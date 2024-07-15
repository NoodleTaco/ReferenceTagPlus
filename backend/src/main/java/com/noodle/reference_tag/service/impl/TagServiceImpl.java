package com.noodle.reference_tag.service.impl;

import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.domain.entity.TagEntity;
import com.noodle.reference_tag.mapper.impl.TagMapper;
import com.noodle.reference_tag.repository.TagRepository;
import com.noodle.reference_tag.service.ImageTagService;
import com.noodle.reference_tag.service.TagService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    /**
     * Holds the reference to the repository that will be injected
     */
    private final TagRepository tagRepository;

    private final ImageTagService imageTagService;

    private final TagMapper tagMapper;

    /**
     * Constructor that will instantiate the repository
     *
     * @param tagRepository   The Tag Repository that will be injected
     * @param imageTagService
     * @param tagMapper
     */
    public TagServiceImpl(TagRepository tagRepository, ImageTagService imageTagService, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.imageTagService = imageTagService;
        this.tagMapper = tagMapper;
    }

    /**
     * Saves a tagEntity to the DB
     * @param tagDto The Tag Entity to be saved
     * @return A copy of the Tag Entity
     */
    @Override
    public TagDto save(TagDto tagDto) {
        TagEntity tagEntity = tagMapper.mapFrom(tagDto);
        TagEntity savedEntity = tagRepository.save(tagEntity);
        return tagMapper.mapTo(savedEntity);
    }

    /**
     * Attempts to return a Tag correlating to a provided id, wrapping it in an Optional container
     * @param id The id to be searched for
     * @return The Optional container that holds the entity or is empty
     */
    @Override
    public Optional<TagDto> findTagById(Long id) {
        Optional<TagEntity> foundTag = tagRepository.findById(id);
        if(foundTag.isPresent()){
            return Optional.of(tagMapper.mapTo(foundTag.get()));
        }
        else{
            return Optional.empty();
        }
    }

    /**
     * Return the list of all tags
     * @return The list of all tags
     */
    @Override
    public List<TagDto> findAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::mapTo)
                .collect(Collectors.toList());
    }

    /**
     * Return whether a given tag exists in the db
     * @param id The id of the tag being searched for
     * @return Whether the tag exists
     */
    @Override
    public boolean isExists(Long id) {
        return tagRepository.existsById(id);
    }

    /**
     * Delete the provided tag
     * @param id The id of the tag to be deleted
     */
    @Override
    public void deleteTagById(Long id) {
        imageTagService.deleteByTagId(id);
        tagRepository.deleteById(id);
    }

    @Override
    public Optional<TagDto> findByName(String name) {
        Optional<TagEntity> foundTag = tagRepository.findByNameIgnoreCase(name);

        if(foundTag.isPresent()){
            return Optional.of(tagMapper.mapTo(foundTag.get()));
        }
        else{
            return Optional.empty();
        }
    }
}
