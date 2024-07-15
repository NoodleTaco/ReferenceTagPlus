package com.noodle.reference_tag.unit;

import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.domain.entity.TagEntity;
import com.noodle.reference_tag.mapper.impl.TagMapper;
import com.noodle.reference_tag.repository.TagRepository;
import com.noodle.reference_tag.service.ImageTagService;
import com.noodle.reference_tag.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ImageTagService imageTagService;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        TagDto inputDto = new TagDto();
        TagEntity entity = new TagEntity();
        TagEntity savedEntity = new TagEntity();
        TagDto outputDto = new TagDto();

        when(tagMapper.mapFrom(inputDto)).thenReturn(entity);
        when(tagRepository.save(entity)).thenReturn(savedEntity);
        when(tagMapper.mapTo(savedEntity)).thenReturn(outputDto);

        TagDto result = tagService.save(inputDto);

        assertEquals(outputDto, result);
        verify(tagRepository).save(entity);
    }

    @Test
    void testFindTagById_Found() {
        Long id = 1L;
        TagEntity entity = new TagEntity();
        TagDto dto = new TagDto();

        when(tagRepository.findById(id)).thenReturn(Optional.of(entity));
        when(tagMapper.mapTo(entity)).thenReturn(dto);

        Optional<TagDto> result = tagService.findTagById(id);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void testFindTagById_NotFound() {
        Long id = 1L;

        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        Optional<TagDto> result = tagService.findTagById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllTags() {
        List<TagEntity> entities = Arrays.asList(new TagEntity(), new TagEntity());
        List<TagDto> dtos = Arrays.asList(new TagDto(), new TagDto());

        when(tagRepository.findAll()).thenReturn(entities);
        when(tagMapper.mapTo(any(TagEntity.class))).thenReturn(dtos.get(0), dtos.get(1));

        List<TagDto> result = tagService.findAllTags();

        assertEquals(2, result.size());
        assertEquals(dtos, result);
    }

    @Test
    void testIsExists() {
        Long id = 1L;

        when(tagRepository.existsById(id)).thenReturn(true);

        assertTrue(tagService.isExists(id));
    }

    @Test
    void testDeleteTagById() {
        Long id = 1L;

        tagService.deleteTagById(id);

        verify(imageTagService).deleteByTagId(id);
        verify(tagRepository).deleteById(id);
    }

    @Test
    void testFindByName_Found() {
        String name = "TestTag";
        TagEntity entity = new TagEntity();
        TagDto dto = new TagDto();

        when(tagRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(entity));
        when(tagMapper.mapTo(entity)).thenReturn(dto);

        Optional<TagDto> result = tagService.findByName(name);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void testFindByName_NotFound() {
        String name = "TestTag";

        when(tagRepository.findByNameIgnoreCase(name)).thenReturn(Optional.empty());

        Optional<TagDto> result = tagService.findByName(name);

        assertFalse(result.isPresent());
    }
}