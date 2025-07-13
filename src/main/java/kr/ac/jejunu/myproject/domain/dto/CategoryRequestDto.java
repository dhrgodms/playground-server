package kr.ac.jejunu.myproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {
    private String categoryName;
    private String categoryColor;
    private String categoryDescription;
    private String categoryPath;
}