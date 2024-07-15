package kr.ac.jejunu.myproject.File;

import kr.ac.jejunu.myproject.Post.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;


    @Autowired
    private FileDao fileDao;
    @Autowired
    private PostDao postDao;

    public String storeFile(MultipartFile file) throws IOException {
        try {
            String absolutePath = System.getProperty("user.dir") + uploadDir;
            // 디렉토리 확인 및 생성
            File dir = new File(absolutePath);
            if (!dir.exists()) {
                dir.mkdirs(); // 디렉토리가 없으면 생성
            }

            File dest = new File(absolutePath + file.getOriginalFilename());
            file.transferTo(dest);

            saveFilePathToDatabase(uploadDir + file.getOriginalFilename());

            return "File uploaded successfully: " + dest.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }

    private void saveFilePathToDatabase(String filePath) {
        Long postId = postDao.findTop1ByOrderByIdDesc().getId();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilePath(filePath);
        // Ensure fileRepository is autowired or instantiated
        fileEntity.setPostId(postId + 1);
        fileDao.save(fileEntity);
    }
}
