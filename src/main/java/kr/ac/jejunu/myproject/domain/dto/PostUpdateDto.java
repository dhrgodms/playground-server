package kr.ac.jejunu.myproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    private Long id;
    private String content;
    private String contentTitle;
    private List<Long> tags;
    private Long categoryId;
    private String thumbnail;
    private List<String> fileUrls;
}
