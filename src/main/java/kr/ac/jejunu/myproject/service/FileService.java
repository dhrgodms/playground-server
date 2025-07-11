package kr.ac.jejunu.myproject.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import kr.ac.jejunu.myproject.domain.FileEntity;
import kr.ac.jejunu.myproject.repository.FileDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileDao fileDao;

    public void save(Long postId, String fileUrl) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPostId(postId);
        fileEntity.setFilePath(fileUrl);
        fileDao.save(fileEntity);
    }

    public void update(Long postId, String fileUrl) {
        List<FileEntity> files = fileDao.findByPostId(postId);
        fileDao.deleteAll(files);
        FileEntity newFileEntity = new FileEntity();
        newFileEntity.setPostId(postId);
        newFileEntity.setFilePath(fileUrl);
        fileDao.save(newFileEntity);
    }

    public List<String> getFileUrlsByPostId(Long postId) {
        return fileDao.findByPostId(postId)
                .stream()
                .map(FileEntity::getFilePath)
                .collect(java.util.stream.Collectors.toList());
    }
}