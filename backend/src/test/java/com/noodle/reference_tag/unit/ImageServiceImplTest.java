package com.noodle.reference_tag.unit;

import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.entity.ImageEntity;
import com.noodle.reference_tag.mapper.impl.ImageMapper;
import com.noodle.reference_tag.repository.ImageRepository;
import com.noodle.reference_tag.service.ImageTagService;
import com.noodle.reference_tag.service.impl.ImageServiceImpl;
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

public class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageTagService imageTagService;

    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ImageDto inputDto = new ImageDto();
        ImageEntity entity = new ImageEntity();
        ImageEntity savedEntity = new ImageEntity();
        ImageDto outputDto = new ImageDto();

        when(imageMapper.mapFrom(inputDto)).thenReturn(entity);
        when(imageRepository.save(entity)).thenReturn(savedEntity);
        when(imageMapper.mapTo(savedEntity)).thenReturn(outputDto);

        ImageDto result = imageService.save(inputDto);

        assertEquals(outputDto, result);
        verify(imageRepository).save(entity);
    }

    @Test
    void testFindImageById_Found() {
        Long id = 1L;
        ImageEntity entity = new ImageEntity();
        ImageDto dto = new ImageDto();

        when(imageRepository.findById(id)).thenReturn(Optional.of(entity));
        when(imageMapper.mapTo(entity)).thenReturn(dto);

        Optional<ImageDto> result = imageService.findImageById(id);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void testFindImageById_NotFound() {
        Long id = 1L;

        when(imageRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ImageDto> result = imageService.findImageById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllImages() {
        List<ImageEntity> entities = Arrays.asList(new ImageEntity(), new ImageEntity());
        List<ImageDto> dtos = Arrays.asList(new ImageDto(), new ImageDto());

        when(imageRepository.findAll()).thenReturn(entities);
        when(imageMapper.mapTo(any(ImageEntity.class))).thenReturn(dtos.get(0), dtos.get(1));

        List<ImageDto> result = imageService.findAllImages();

        assertEquals(2, result.size());
        assertEquals(dtos, result);
    }

    @Test
    void testIsExists() {
        Long id = 1L;

        when(imageRepository.existsById(id)).thenReturn(true);

        assertTrue(imageService.isExists(id));
    }

    @Test
    void testDeleteImageById() {
        Long id = 1L;

        imageService.deleteImageById(id);

        verify(imageTagService).deleteByImageId(id);
        verify(imageRepository).deleteById(id);
    }

    @Test
    void testFindByPath_Found() {
        String path = "test/path";
        ImageEntity entity = new ImageEntity();
        ImageDto dto = new ImageDto();

        when(imageRepository.findByPathIgnoreCase(path)).thenReturn(Optional.of(entity));
        when(imageMapper.mapTo(entity)).thenReturn(dto);

        Optional<ImageDto> result = imageService.findByPath(path);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void testFindByPath_NotFound() {
        String path = "test/path";

        when(imageRepository.findByPathIgnoreCase(path)).thenReturn(Optional.empty());

        Optional<ImageDto> result = imageService.findByPath(path);

        assertFalse(result.isPresent());
    }
}