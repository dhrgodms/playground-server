package kr.ac.jejunu.myproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.ac.jejunu.myproject.domain.Post;
import kr.ac.jejunu.myproject.domain.dto.PostResponseDto;

@Mapper(componentModel = "spring", uses = { TagMapper.class, CategoryMapper.class })
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "tags", expression = "java(post.getPostTags().stream().map(postTag -> TagMapper.INSTANCE.toTagResponseDto(postTag.getTag())).toList())")
    @Mapping(target = "category", expression = "java(post.getCategory() != null ? CategoryMapper.INSTANCE.toCategoryResponseDto(post.getCategory()) : null)")
    @Mapping(target = "fileUrls", ignore = true)
    @Mapping(target = "modifiedDate", source = "lastModifiedDate")
    PostResponseDto toPostResponseDto(Post post);
}