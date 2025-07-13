package kr.ac.jejunu.myproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDto {
    private String tagName;
    private String tagColor;
    private String tagDescription;
}
