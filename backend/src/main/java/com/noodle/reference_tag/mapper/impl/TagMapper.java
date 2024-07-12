package com.noodle.reference_tag.mapper.impl;

import com.noodle.reference_tag.domain.dto.TagDto;
import com.noodle.reference_tag.domain.entity.TagEntity;
import com.noodle.reference_tag.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapper implements Mapper<TagEntity, TagDto> {

    private ModelMapper modelMapper;

    public TagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TagDto mapTo(TagEntity tagEntity) {
        return modelMapper.map(tagEntity, TagDto.class);
    }

    @Override
    public TagEntity mapFrom(TagDto tagDto) {
        return modelMapper.map(tagDto, TagEntity.class);
    }
}
