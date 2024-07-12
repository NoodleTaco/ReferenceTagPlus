package com.noodle.reference_tag.mapper.impl;

import com.noodle.reference_tag.domain.dto.ImageTagDto;
import com.noodle.reference_tag.domain.entity.ImageTagEntity;
import com.noodle.reference_tag.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageTagMapper implements Mapper<ImageTagEntity, ImageTagDto> {

    private ModelMapper modelMapper;

    public ImageTagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public ImageTagDto mapTo(ImageTagEntity imageTagEntity) {
        return modelMapper.map(imageTagEntity, ImageTagDto.class);
    }

    @Override
    public ImageTagEntity mapFrom(ImageTagDto imageTagDto) {
        return modelMapper.map(imageTagDto, ImageTagEntity.class);
    }
}
