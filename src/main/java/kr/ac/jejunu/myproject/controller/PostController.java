package kr.ac.jejunu.myproject.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.jejunu.myproject.domain.Post;
import kr.ac.jejunu.myproject.domain.dto.PostRequestDto;
import kr.ac.jejunu.myproject.domain.dto.PostResponseDto;
import kr.ac.jejunu.myproject.domain.dto.PostUpdateDto;
import kr.ac.jejunu.myproject.repository.PostRepository;
import kr.ac.jejunu.myproject.service.FileService;
import kr.ac.jejunu.myproject.service.PostService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final FileService fileService;

    @Value("${myapp.hostname}")
    private String hostname;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(@RequestParam(required = false) Integer categoryId,
            Pageable pageable) {
        if (categoryId != null) {
            return ResponseEntity.ok(postService.getPostsByCategoryId(categoryId, pageable).map(PostResponseDto::new));
        }
        Page<Post> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts.map(PostResponseDto::new));
    }

    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody PostRequestDto dto) {
        Post post = postService.savePost(dto);
        if (dto.getFileUrls() != null) {
            for (String fileUrl : dto.getFileUrls()) {
                fileService.save(post.getId(), fileUrl);
            }
        }
        return ResponseEntity.ok(new PostResponseDto(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostWithFiles(@PathVariable Long id) {
        Post post = postService.getByPostId(id);
        List<String> fileUrls = fileService.getFileUrlsByPostId(id);
        // fileUrls는 별도로 처리하거나 post에 추가
        return ResponseEntity.ok(new PostResponseDto(post));
    }

    @PostMapping("/{id}/files")
    public ResponseEntity<?> addFilesToPost(@PathVariable Long id, @RequestBody Map<String, List<String>> request) {
        List<String> fileUrls = request.get("fileUrls");
        if (fileUrls != null) {
            for (String fileUrl : fileUrls) {
                fileService.save(id, fileUrl);
            }
        }
        return ResponseEntity.ok().body(Map.of("message", "Files added successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        postUpdateDto.setId(id);
        Post updatedPost = postService.updatePost(postUpdateDto);
        return ResponseEntity.ok(new PostResponseDto(updatedPost));
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
    public ResponseEntity<List<PostResponseDto>> getRecentPosts() {
        List<Post> posts = postService.getRecentPostsByAllTags();
        List<PostResponseDto> responseDtos = posts.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseDtos);
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
        Post post = postRepository.findById(id).get();
        post.setThumbnail(hostname + ":8080/thumbnail/white.jpg");
        postRepository.save(post);
    }
}
