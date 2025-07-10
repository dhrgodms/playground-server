package kr.ac.jejunu.myproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.jejunu.myproject.domain.Comment;
import kr.ac.jejunu.myproject.domain.Post;
import kr.ac.jejunu.myproject.repository.CommentDao;
import kr.ac.jejunu.myproject.repository.PostDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PostController {
    private final PostDao postDao;
    private final CommentDao commentDao;

    @GetMapping("/{id}")
    public Post get(@PathVariable Long id) {
        postDao.findById(id).get().setViews(postDao.findById(id).get().getViews() + 1);
        postDao.save(postDao.findById(id).get());
        return postDao.findById(id).get();
    }

    @GetMapping("/all")
    public List<Post> getAllPosts() {
        return postDao.findAll();
    }

    @GetMapping("/tag/{id}")
    public List<Post> getTagPosts(@PathVariable Integer id) {
        return postDao.findAllByTag(id);
    }

    @PostMapping("/add")
    public Post add(@RequestBody Post post) {
        return postDao.save(post);
    }

    @Value("${myapp.hostname}")
    private String hostname;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/thumbnail-upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        try {
            String absolutePath = System.getProperty("user.dir") + uploadDir + "thumbnail/";
            // 디렉토리 확인 및 생성
            File dir = new File(absolutePath);
            if (!dir.exists()) {
                dir.mkdirs(); // 디렉토리가 없으면 생성
            }

            File dest = new File(absolutePath + file.getOriginalFilename());
            file.transferTo(dest);

            return uploadDir + "thumbnail/" + file.getOriginalFilename();

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }

    @PostMapping("/update")
    public Post update(@RequestBody Post post) {
        System.out.println(post.getId());
        Post updatedPost = postDao.findById(post.getId()).get();
        updatedPost.setContentTitle(post.getContentTitle());
        updatedPost.setContent(post.getContent());
        updatedPost.setThumbnail(post.getThumbnail());
        return postDao.save(updatedPost);
    }

    @GetMapping("/main-posts")
    public List<Post> getRecentPosts() {
        List<Post> postList = new ArrayList<>();
        try {
            postList.add(postDao.findTop1ByOrderByViewsDesc()); // 인기글 // 조회수 내림차순 첫번째
            postList.add(postDao.findTop1ByOrderByIdDesc()); // 최신글 // 업로드 날짜 제일 빠른 게시글
            postList.add(postDao.findTop1ByTagOrderByIdDesc(1)); // 생각글 // tag 값이 1
            postList.add(postDao.findTop1ByTagOrderByIdDesc(2)); // 만화글 // tag 값이 2
            postList.add(postDao.findTop1ByTagOrderByIdDesc(3)); // 플리글 // tag 값이 3

            postList = postList.stream()
                    .map(post -> {
                        List<Comment> comment = commentDao.findAllByPostId(post.getId());
                        post.setCommentCount((long) comment.size());
                        return post;
                    }).toList();

        } catch (Exception e) {
            Post post = new Post("test", "testTitle");
            postDao.save(post);
            postList.add(post);
        }

        return postList;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        postDao.deleteById(id);
    }

    @GetMapping("/thumbnail-delete/{id}")
    public void deleteThumbnail(@PathVariable Long id) {
        Post post = postDao.findById(id).get();
        post.setThumbnail(hostname + ":8080/thumbnail/white.jpg");
        postDao.save(post);
    }

    @GetMapping("/like/{id}")
    public Long getLikes(@PathVariable Long id) {
        postDao.findById(id).get().setLikes(postDao.findById(id).get().getLikes() + 1);
        postDao.save(postDao.findById(id).get());
        return postDao.findById(id).get().getLikes();
    }
}
