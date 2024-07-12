package com.noodle.reference_tag.mapper.impl;

import com.noodle.reference_tag.domain.dto.ImageDto;
import com.noodle.reference_tag.domain.entity.ImageEntity;
import com.noodle.reference_tag.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper implements Mapper<ImageEntity, ImageDto> {

    private ModelMapper modelMapper;

    public ImageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public ImageDto mapTo(ImageEntity imageEntity) {
        return modelMapper.map(imageEntity, ImageDto.class);
    }

    @Override
    public ImageEntity mapFrom(ImageDto imageDto) {
        return modelMapper.map(imageDto, ImageEntity.class);
    }
}
