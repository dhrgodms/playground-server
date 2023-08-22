package kr.ac.jejunu.myproject.Image;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.jejunu.myproject.Post.PostDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
public class ImageController {
    private final ImageDao imageDao;
    private final PostDao postDao;
    @GetMapping("/{id}")
    public Image get(@PathVariable Long id){
        return imageDao.findById(id).get();
    }

    @PostMapping("/add")
    public List<String> upload(@RequestParam("file") List<MultipartFile> files, HttpServletRequest request) throws IOException {
        List<String> uploadedFilesUrls = new ArrayList<>();
        System.out.println("files : "+files);
        File path = new File(request.getServletContext().getRealPath("/") + "/static/");

        for (MultipartFile file : files) {
            FileOutputStream fileOutputStream = new FileOutputStream(path + file.getOriginalFilename());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(file.getBytes());
            bufferedOutputStream.close();

            uploadedFilesUrls.add("http://localhost:8080/images/" + file.getOriginalFilename());
        }

        for (String url : uploadedFilesUrls){
            Long postId = postDao.findTop1ByOrderByIdDesc().getId();
            Image image = new Image();
            image.setUrl(url);
            image.setPostId(postId+1);
            imageDao.save(image);
        };
        return uploadedFilesUrls;
    }

    @GetMapping("/all/{id}")
    public List<Image> getAll(@PathVariable Long id){
        return imageDao.findByPostId(id);
    }


}