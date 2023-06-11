package kr.ac.jejunu.myproject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostDao extends JpaRepository<Post, Long>{

    List<Post> findTop5ByOrderByIdDesc();
    Post findTop1ByOrderByIdDesc();
}