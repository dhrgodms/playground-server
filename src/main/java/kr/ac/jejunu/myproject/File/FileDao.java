package kr.ac.jejunu.myproject.File;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileDao extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByPostId(Long id);
}
