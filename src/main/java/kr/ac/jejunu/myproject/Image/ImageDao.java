package kr.ac.jejunu.myproject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Long> {
    List<Image> findByPostId(Long id);
}
