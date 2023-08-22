package kr.ac.jejunu.myproject.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostDao extends JpaRepository<Post, Long>{

    List<Post> findTop5ByOrderByIdDesc();
    Post findTop1ByOrderByIdDesc();
    Post findTop1ByTagOrderById(int tag);

    Post findTop1ByOrderByViewsDesc();

    Long findTopByOrderByIdDesc();

    List<Post> findAllByTag(Integer tag);

    Post findTop1ByTagOrderByIdDesc(int i);
}