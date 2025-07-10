package kr.ac.jejunu.myproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    private Long id;
    private String content;
    private String contentTitle;
    private Integer tag;
    private String thumbnail;
}
