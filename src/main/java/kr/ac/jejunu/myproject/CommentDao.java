package kr.ac.jejunu.myproject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);

}
