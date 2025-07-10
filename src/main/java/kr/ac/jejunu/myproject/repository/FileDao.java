package kr.ac.jejunu.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.jejunu.myproject.domain.FileEntity;

import java.util.List;

public interface FileDao extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByPostId(Long id);
}
