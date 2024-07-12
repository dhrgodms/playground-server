package kr.ac.jejunu.myproject.File;

import kr.ac.jejunu.myproject.Post.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;



    @Autowired
    private FileDao fileDao;
    @Autowired
    private PostDao postDao;

    public String storeFile(MultipartFile file) throws IOException {
        // Ensure upload directory exists
        Path uploadPath = Paths.get(uploadDir+ File.separator+file.getOriginalFilename());

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Copy file to the target location (replacing existing file with the same name)
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

        // Save file path to database
        saveFilePathToDatabase(uploadPath.toString());

        return uploadPath.toString();
    }

    private void saveFilePathToDatabase(String filePath) {
        Long postId = postDao.findTop1ByOrderByIdDesc().getId();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilePath(filePath);
        // Ensure fileRepository is autowired or instantiated
        fileEntity.setPostId(postId);
        fileDao.save(fileEntity);
    }
}
