package kr.ac.jejunu.myproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.jejunu.myproject.domain.Post;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {
    List<Post> findTop5ByOrderByIdDesc();

    Post findTop1ByOrderByIdDesc();

    Post findTop1ByTagOrderById(int tag);

    Post findTop1ByOrderByViewsDesc();

    Long findTopByOrderByIdDesc();

    Post findTop1ByTagOrderByIdDesc(int i);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByTag(Integer tag, Pageable pageable);
}