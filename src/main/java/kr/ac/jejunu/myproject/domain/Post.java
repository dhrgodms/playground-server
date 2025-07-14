package kr.ac.jejunu.myproject.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불필요한 기본 생성자를 만들지 않는다.
public class Post extends BaseTimeEntity {
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
    @JsonManagedReference
    private Set<PostTag> postTags = new HashSet<>();
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

    public void setViews(Long views) {
        this.views = views;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}