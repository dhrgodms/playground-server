package kr.ac.jejunu.myproject.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.ac.jejunu.myproject.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        this.version = post.getVersion();
        this.thumbnail = post.getThumbnail();
        this.views = post.getViews();
        this.likes = post.getLikes();
        this.commentCount = post.getCommentCount();

        // Category 매핑
        if (post.getCategory() != null) {
            this.category = new CategoryResponseDto(post.getCategory());
        }

        // Tags 매핑
        if (post.getPostTags() != null && !post.getPostTags().isEmpty()) {
            this.tags = post.getPostTags().stream()
                    .map(postTag -> new TagResponseDto(postTag.getTag()))
                    .collect(Collectors.toList());
        }
    }

    public List<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(List<String> fileUrls) {
        this.fileUrls = fileUrls;
    }
}
