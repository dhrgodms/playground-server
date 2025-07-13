package kr.ac.jejunu.myproject.domain.dto;

import kr.ac.jejunu.myproject.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {
    private Long id;
    private String tagName;
    private String tagColor;
    private String tagDescription;

    public TagResponseDto(Tag tag) {
        this.id = tag.getId();
        this.tagName = tag.getTagName();
        this.tagColor = tag.getTagColor();
        this.tagDescription = tag.getTagDescription();
    }
}
