package kr.ac.jejunu.myproject.domain.dto;

import kr.ac.jejunu.myproject.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String categoryName;
    private String categoryColor;
    private String categoryDescription;
    private String categoryPath;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.categoryColor = category.getCategoryColor();
        this.categoryDescription = category.getCategoryDescription();
        this.categoryPath = category.getCategoryPath();
    }
}