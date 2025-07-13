package kr.ac.jejunu.myproject.domain.dto;

import kr.ac.jejunu.myproject.domain.Category;
import kr.ac.jejunu.myproject.domain.PostTag;
import kr.ac.jejunu.myproject.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import kr.ac.jejunu.myproject.domain.Post;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String content;
    private String contentTitle;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long version;
    String thumbnail;
    private List<TagResponseDto> tags;
    private CategoryResponseDto category;
    private Long views; // 조회수
    private Long likes; // 좋아요 수
    private Long commentCount;
    private List<String> fileUrls;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.contentTitle = post.getContentTitle();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getLastModifiedDate();
        //this.category도 따로 매핑
        this.version = post.getVersion();
        this.thumbnail = post.getThumbnail();
        //this.tags는 따로 매핑
        this.views = post.getViews();
        this.likes = post.getLikes();
        this.commentCount = post.getCommentCount();
    }



    public List<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(List<String> fileUrls) {
        this.fileUrls = fileUrls;
    }
}
