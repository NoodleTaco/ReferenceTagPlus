package com.noodle.reference_tag.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageTagDto {

    private Long id;

    private ImageDto image;

    private TagDto tag;
}
