package kr.ac.jejunu.myproject.Post;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.jejunu.myproject.Comment.Comment;
import kr.ac.jejunu.myproject.Comment.CommentDao;
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
@RequestMapping("/api/post")
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
public class PostController {
    private final PostDao postDao;
    private final CommentDao commentDao;


    @GetMapping("/{id}")
    public Post get(@PathVariable Long id){
        postDao.findById(id).get().setViews(postDao.findById(id).get().getViews()+1);
        postDao.save(postDao.findById(id).get());
        return postDao.findById(id).get();
    }

    @GetMapping("/all")
    public List<Post> getAllPosts(){
        return postDao.findAll();
    }

    @GetMapping("/tag/{id}")
    public List<Post> getTagPosts(@PathVariable Integer id){
        return postDao.findAllByTag(id);
    }

    @PostMapping("/add")
    public Post add(@RequestBody Post post){
        return postDao.save(post);
    }

    @PostMapping("/thumbnail-upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        File path = new File(request.getServletContext().getRealPath("/") + "/static/");
//        path.mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream(path + file.getOriginalFilename());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();

        return "http://localhost:8080/thumbnail/" + file.getOriginalFilename();
    }

    @PostMapping("/update")
    public Post update(@RequestBody Post post){
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

        return postList;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        postDao.deleteById(id);
    }

    @GetMapping("/thumbnail-delete/{id}")
    public void deleteThumbnail(@PathVariable Long id){
        Post post = postDao.findById(id).get();
        post.setThumbnail("http://localhost:8080/thumbnail/white.jpg");
        postDao.save(post);
    }

    @GetMapping("/like/{id}")
    public Long getLikes(@PathVariable Long id){
        postDao.findById(id).get().setLikes(postDao.findById(id).get().getLikes()+1);
        postDao.save(postDao.findById(id).get());
        return postDao.findById(id).get().getLikes();
    }
}
