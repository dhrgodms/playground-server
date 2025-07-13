package kr.ac.jejunu.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import kr.ac.jejunu.myproject.domain.Comment;
import kr.ac.jejunu.myproject.repository.CommentRepository;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @PostMapping("/add")
    public Comment add(@RequestBody Comment comment) {
        System.out.println(comment);
        return commentRepository.save(comment);
    }

    @GetMapping("/{id}")
    public Comment get(@PathVariable Long id) {
        return commentRepository.findById(id).get();
    }

    @GetMapping("/all/{id}")
    public List<Comment> getAllByPostId(@PathVariable Long id) {
        System.out.println(commentRepository.findAllByPostId(id));
        return commentRepository.findAllByPostId(id);
    }

}
