package com.noodle.reference_tag.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.service.TagService;
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

@WebMvcTest(TagController.class)
public class TagControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateTag() throws Exception {
        TagDto tagDto = new TagDto();
        tagDto.setName("Nature");

        when(tagService.save(any(TagDto.class))).thenReturn(tagDto);

        mockMvc.perform(post("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nature"));
    }

    @Test
    public void testGetTag() throws Exception {
        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("Nature");

        when(tagService.findTagById(1L)).thenReturn(Optional.of(tagDto));

        mockMvc.perform(get("/api/tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nature"));
    }

    @Test
    public void testGetTagNotFound() throws Exception {
        when(tagService.findTagById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tags/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllTags() throws Exception {
        TagDto tag1 = new TagDto();
        tag1.setId(1L);
        tag1.setName("Nature");

        TagDto tag2 = new TagDto();
        tag2.setId(2L);
        tag2.setName("Urban");

        when(tagService.findAllTags()).thenReturn(Arrays.asList(tag1, tag2));

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Nature"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Urban"));
    }

    @Test
    public void testDeleteTag() throws Exception {
        mockMvc.perform(delete("/api/tags/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetTagByName() throws Exception {
        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("Nature");

        when(tagService.findByName("Nature")).thenReturn(Optional.of(tagDto));

        mockMvc.perform(get("/api/tags/name").param("name", "Nature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nature"));
    }
}
