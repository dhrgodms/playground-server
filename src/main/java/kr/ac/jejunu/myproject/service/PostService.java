package kr.ac.jejunu.myproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.ac.jejunu.myproject.domain.Comment;
import kr.ac.jejunu.myproject.domain.Post;
import kr.ac.jejunu.myproject.domain.dto.PostRequestDto;
import kr.ac.jejunu.myproject.domain.dto.PostUpdateDto;
import kr.ac.jejunu.myproject.repository.CommentDao;
import kr.ac.jejunu.myproject.repository.PostDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostDao postDao;
    private final CommentDao commentDao;

    public Post getByPostId(Long id) {
        Post post = postDao.findById(id).get();
        post.setViews(post.getViews() + 1);
        return postDao.save(post);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postDao.findAll(pageable);
    }

    public Page<Post> getTagPosts(Integer tag, Pageable pageable) {
        return postDao.findAllByTag(tag, pageable);
    }

    public Post savePost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto.getContent(), postRequestDto.getContentTitle());
        post.setTag(postRequestDto.getTag());
        post.setThumbnail(postRequestDto.getThumbnail());
        return postDao.save(post);
    }

    public Post updatePost(PostUpdateDto postUpdateDto) {
        Post post = postDao.findById(postUpdateDto.getId()).get();
        post.setContentTitle(postUpdateDto.getContentTitle());
        post.setContent(postUpdateDto.getContent());
        post.setThumbnail(postUpdateDto.getThumbnail());
        return postDao.save(post);
    }

    public void deletePost(Long id) {
        try {
            postDao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Post not found");
        }
    }

    public void likePost(Long id) {
        Post post = postDao.findById(id).get();
        post.setLikes(post.getLikes() + 1);
        postDao.save(post);
    }

    public Long getLikes(Long id) {
        Post post = postDao.findById(id).get();
        return post.getLikes();
    }

    public List<Post> getRecentPostsByAllTags() {
        List<Post> postList = new ArrayList<>();
        try {
            postList.add(postDao.findTop1ByOrderByViewsDesc()); // 인기글 // 조회수 내림차순 첫번째
            postList.add(postDao.findTop1ByOrderByIdDesc()); // 최신글 // 업로드 날짜 제일 빠른 게시글
            postList.add(postDao.findTop1ByTagOrderByIdDesc(1)); // 생각글 // tag 값이 1
            postList.add(postDao.findTop1ByTagOrderByIdDesc(2)); // 만화글 // tag 값이 2
            postList.add(postDao.findTop1ByTagOrderByIdDesc(3)); // 플리글 // tag 값이 3

            // TODO n+1 해결
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

}
