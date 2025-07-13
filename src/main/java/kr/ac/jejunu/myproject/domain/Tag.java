package kr.ac.jejunu.myproject.domain;

import jakarta.persistence.*;
import kr.ac.jejunu.myproject.domain.dto.TagResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @GeneratedValue @Id
    @Column(name = "tag_id")
    private Long id;
    private String tagName;
    private String tagColor;
    private String tagDescription;
    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags = new ArrayList<>();

    public Tag(String tagName, String tagColor, String tagDescription) {
        this.tagName = tagName;
        this.tagColor = tagColor;
        this.tagDescription = tagDescription;
    }

    public void addTag(PostTag postTag) {
        this.postTags.add(postTag);
    }
}
