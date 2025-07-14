package kr.ac.jejunu.myproject.domain;

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
public class Category extends BaseTimeEntity {
    @GeneratedValue
    @Id
    @Column(name = "category_id")
    private Long id;

    private String categoryName;
    private String categoryColor;
    private String categoryDescription;
    private String categoryPath;

    @OneToMany(mappedBy = "category")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private List<Post> posts;

    public Category(String categoryName, String categoryColor, String categoryDescription, String categoryPath) {
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.categoryDescription = categoryDescription;
        this.categoryPath = categoryPath;
    }

    public void addPosts(Post post) {
        this.posts.add(post);
    }
}
