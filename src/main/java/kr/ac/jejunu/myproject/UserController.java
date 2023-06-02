package kr.ac.jejunu.myproject;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor // 생성자 자동으로 생성-> 자동으로 의존성 주입
public class UserController {
    private final UserDao userDao;
    @GetMapping("/{id}")
    public User get(@PathVariable Long id){
        return userDao.findById(id).get();
    }


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        File path = new File(request.getServletContext().getRealPath("/") + "/static/");
//        path.mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream(path + file.getOriginalFilename());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();

        return "http://localhost:8080/" + file.getOriginalFilename();
    }
}
