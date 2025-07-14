package kr.ac.jejunu.myproject.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @GeneratedValue
    @Id
    @Column(name = "tag_id")
    private Long id;
    private String tagName;
    private String tagColor;
    private String tagDescription;
    @OneToMany(mappedBy = "tag")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private List<PostTag> postTags = new ArrayList<>();

    public Tag(String tagName, String tagColor, String tagDescription) {
        this.tagName = tagName;
        this.tagColor = tagColor;
        this.tagDescription = tagDescription;
    }

    public void addPostTag(PostTag postTag) {
        this.postTags.add(postTag);
    }
}
