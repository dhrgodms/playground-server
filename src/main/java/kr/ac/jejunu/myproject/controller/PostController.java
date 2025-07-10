package kr.ac.jejunu.myproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.jejunu.myproject.domain.Post;
import kr.ac.jejunu.myproject.domain.dto.PostRequestDto;
import kr.ac.jejunu.myproject.domain.dto.PostResponseDto;
import kr.ac.jejunu.myproject.domain.dto.PostUpdateDto;
import kr.ac.jejunu.myproject.repository.PostDao;
import kr.ac.jejunu.myproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v2/posts")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PostController {

    private final PostDao postDao;
    private final PostService postService;

    @Value("${myapp.hostname}")
    private String hostname;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(@RequestParam(required = false) Integer tag,
            Pageable pageable) {
        if (tag != null) {
            return ResponseEntity.ok(postService.getTagPosts(tag, pageable).map(PostResponseDto::new));
        }
        Page<Post> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts.map(PostResponseDto::new));
    }

    @PostMapping
    public PostResponseDto add(@RequestBody PostRequestDto postRequestDto) {
        return new PostResponseDto(postService.savePost(postRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(new PostResponseDto(postService.getByPostId(id)));
    }

    @PutMapping("/{id}")
    public PostResponseDto update(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        postUpdateDto.setId(id);
        return new PostResponseDto(postService.updatePost(postUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Long> getLikes(@PathVariable Long id) {
        postService.likePost(id);
        return ResponseEntity.ok(postService.getLikes(id));
    }

    @GetMapping("/main-posts") // TODO 손보기
    public List<Post> getRecentPosts() {
        return postService.getRecentPostsByAllTags();
    }

    @PostMapping("/thumbnail-upload") // TODO 손보기
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

    @GetMapping("/thumbnail-delete/{id}") // TODO 손보기
    public void deleteThumbnail(@PathVariable Long id) {
        Post post = postDao.findById(id).get();
        post.setThumbnail(hostname + ":8080/thumbnail/white.jpg");
        postDao.save(post);
    }

}
