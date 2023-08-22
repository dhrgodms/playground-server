package kr.ac.jejunu.myproject.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String content;
    private String contentTitle;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long version;
    String thumbnail;
    private int tag; // 1=글, 2=그림, 3=플레이리스트
    private Long views; // 조회수
    private Long likes; // 좋아요 수
    private Long commentCount;
}
