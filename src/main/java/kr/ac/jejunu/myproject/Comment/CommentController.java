package kr.ac.jejunu.myproject.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentDao commentDao;
    @PostMapping("/add")
    public Comment add(@RequestBody Comment comment){
        System.out.println(comment);
        return commentDao.save(comment);
    }

    @GetMapping("/{id}")
    public Comment get(@PathVariable Long id){
        return commentDao.findById(id).get();
    }

    @GetMapping("/all/{id}")
    public List<Comment> getAllByPostId(@PathVariable Long id){
        System.out.println(commentDao.findAllByPostId(id));
        return commentDao.findAllByPostId(id);
    }

}
