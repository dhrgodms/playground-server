package kr.ac.jejunu.myproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.ac.jejunu.myproject.domain.Category;
import kr.ac.jejunu.myproject.domain.dto.CategoryResponseDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponseDto toCategoryResponseDto(Category category);
}