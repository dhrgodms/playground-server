package kr.ac.jejunu.myproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.ac.jejunu.myproject.domain.Tag;
import kr.ac.jejunu.myproject.domain.dto.TagResponseDto;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagResponseDto toTagResponseDto(Tag tag);
}