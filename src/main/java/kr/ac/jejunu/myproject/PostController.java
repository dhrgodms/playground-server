package kr.ac.jejunu.myproject;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
public class PostController {
    private final PostDao postDao;


    @GetMapping("/{id}")
    public Post get(@PathVariable Long id){
        return postDao.findById(id).get();
    }

    @GetMapping("/all")
    public List<Post> getAllPosts(){
        return postDao.findAll();
    }

    @PostMapping("/add")
    public Post add(@RequestBody Post post){
        System.out.println("Post:"+post);
        return postDao.save(post);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        File path = new File(request.getServletContext().getRealPath("/") + "/static/");
//        path.mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream(path + file.getOriginalFilename());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();

        return "http://localhost:8080/thumbnail/" + file.getOriginalFilename();
    }

}
