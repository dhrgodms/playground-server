package kr.ac.jejunu.myproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String content;
    private String contentTitle;
    private Integer tag;
    private String thumbnail;
    private List<String> fileUrls;

    public List<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(List<String> fileUrls) {
        this.fileUrls = fileUrls;
    }
}
