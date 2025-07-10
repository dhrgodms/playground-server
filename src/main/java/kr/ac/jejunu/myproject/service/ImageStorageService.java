package kr.ac.jejunu.myproject.service;

import kr.ac.jejunu.myproject.domain.Image;
import kr.ac.jejunu.myproject.repository.ImageDao;
import kr.ac.jejunu.myproject.repository.PostDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private PostDao postDao;

    public String storeImage(MultipartFile file) throws IOException {
        try {
            String absolutePath = System.getProperty("user.dir") + uploadDir + "img/";
            // 디렉토리 확인 및 생성
            File dir = new File(absolutePath);
            if (!dir.exists()) {
                dir.mkdirs(); // 디렉토리가 없으면 생성
            }

            File dest = new File(absolutePath + file.getOriginalFilename());
            file.transferTo(dest);

            saveImagePathToDatabase(uploadDir + "img/" + file.getOriginalFilename());

            return "File uploaded successfully: " + dest.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }

    private void saveImagePathToDatabase(String filePath) {
        Long postId = postDao.findTop1ByOrderByIdDesc().getId();
        Image image = new Image();
        image.setUrl(filePath);
        image.setPostId(postId + 1);
        imageDao.save(image);
    }
}
