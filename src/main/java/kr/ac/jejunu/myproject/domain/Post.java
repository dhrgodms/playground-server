package kr.ac.jejunu.myproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불필요한 기본 생성자를 만들지 않는다.
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String contentTitle;
    @JsonIgnoreProperties({ "posts" })
    @Version // 수정하면 버전이 바뀜
    private Long version;
    @Column(columnDefinition = "VARCHAR(2048)")
    private String thumbnail;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @OneToMany(mappedBy = "post")
    private Set<PostTag> postTags;
    private Long views = 0L; // 조회수
    private Long likes = 0L; // 좋아요 수
    private Long commentCount = 0L;

    public Post(String content, String contentTitle) {
        this.content = content;
        this.contentTitle = contentTitle;
    }

    public void setThumbnail(String url) {
        this.thumbnail = url;
    }

    public void addPostTag(PostTag postTag) {
        this.postTags.add(postTag);
    }
}