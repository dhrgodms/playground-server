package kr.ac.jejunu.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.jejunu.myproject.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);

    Optional<Comment> findByPostId(Long postId);

}
