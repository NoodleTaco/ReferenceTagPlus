package com.noodle.reference_tag.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.dto.ImageTagDto;
import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.service.ImageTagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImageTagController.class)
public class ImageTagControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageTagService imageTagService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAssociateTagWithImage() throws Exception {
        ImageTagDto imageTagDto = new ImageTagDto();
        imageTagDto.setId(1L);

        when(imageTagService.associateTagWithImage(anyLong(), anyLong())).thenReturn(imageTagDto);

        mockMvc.perform(post("/api/image-tags")
                        .param("imageId", "1")
                        .param("tagId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testRemoveTagFromImage() throws Exception {
        mockMvc.perform(delete("/api/image-tags")
                        .param("imageId", "1")
                        .param("tagId", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetTagsForImage() throws Exception {
        TagDto tag1 = new TagDto();
        tag1.setId(1L);
        tag1.setName("Nature");

        TagDto tag2 = new TagDto();
        tag2.setId(2L);
        tag2.setName("Urban");

        when(imageTagService.getTagsForImage(1L)).thenReturn(Arrays.asList(tag1, tag2));

        mockMvc.perform(get("/api/image-tags/image/1/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Nature"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Urban"));
    }

    @Test
    public void testFindImagesByTags() throws Exception {
        ImageDto image1 = new ImageDto();
        image1.setId(1L);
        image1.setPath("/path/to/image1.jpg");

        ImageDto image2 = new ImageDto();
        image2.setId(2L);
        image2.setPath("/path/to/image2.jpg");

        when(imageTagService.findImageBySearchedTags(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(image1, image2));

        mockMvc.perform(get("/api/image-tags/search")
                        .param("tagIds", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].path").value("/path/to/image1.jpg"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].path").value("/path/to/image2.jpg"));
    }
}
