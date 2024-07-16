package com.noodle.reference_tag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noodle.reference_tag.TestDataUtil;
import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ImageController.class)
public class ImageControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateImage() throws Exception {
        ImageDto imageDto = TestDataUtil.createTestImageDtoA();

        when(imageService.save(any(ImageDto.class))).thenReturn(imageDto);

        mockMvc.perform(post("/api/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imageDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path").value(imageDto.getPath()));
    }

    @Test
    public void testGetImage() throws Exception {
        ImageDto imageDto = TestDataUtil.createTestImageDtoA();
        imageDto.setId(1L);
        when(imageService.findImageById(1L)).thenReturn(Optional.of(imageDto));

        mockMvc.perform(get("/api/images/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.path").value(imageDto.getPath()));
    }

    @Test
    public void testGetImageNotFound() throws Exception {
        when(imageService.findImageById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/images/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllImages() throws Exception {
        ImageDto image1 = TestDataUtil.createTestImageDtoA();

        ImageDto image2 = TestDataUtil.createTestImageDtoB();

        when(imageService.findAllImages()).thenReturn(Arrays.asList(image1, image2));

        mockMvc.perform(get("/api/images"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(image1.getId()))
                .andExpect(jsonPath("$[0].path").value(image1.getPath()))
                .andExpect(jsonPath("$[1].id").value(image2.getId()))
                .andExpect(jsonPath("$[1].path").value(image2.getPath()));
    }

    @Test
    public void testDeleteImageEmpty() throws Exception {
        mockMvc.perform(delete("/api/images/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetImageByPath() throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(1L);
        imageDto.setPath("/path/to/image.jpg");

        when(imageService.findByPath("/path/to/image.jpg")).thenReturn(Optional.of(imageDto));

        mockMvc.perform(get("/api/images/path").param("path", "/path/to/image.jpg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.path").value("/path/to/image.jpg"));
    }

}
