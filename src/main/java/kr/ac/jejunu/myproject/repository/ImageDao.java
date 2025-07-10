package kr.ac.jejunu.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.jejunu.myproject.domain.Image;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Long> {
    List<Image> findByPostId(Long id);
}
