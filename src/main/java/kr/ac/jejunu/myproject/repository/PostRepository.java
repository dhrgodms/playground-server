package kr.ac.jejunu.myproject.repository;

import kr.ac.jejunu.myproject.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.jejunu.myproject.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findTop1ByOrderByIdDesc();

    Post findTop1ByOrderByViewsDesc();

    List<Post> findTop2ByCategoryOrderByCreatedDateDesc(Category category);

    Page<Post> findAll(Pageable pageable);

//    Page<Post> findAllByTag(Integer tag, Pageable pageable);

    Page<Post> findAllByCategoryId(Integer categoryId, Pageable pageable);
}