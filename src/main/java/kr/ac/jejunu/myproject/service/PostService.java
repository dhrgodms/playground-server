package kr.ac.jejunu.myproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.ac.jejunu.myproject.domain.Comment;
import kr.ac.jejunu.myproject.domain.Post;
import kr.ac.jejunu.myproject.domain.PostTag;
import kr.ac.jejunu.myproject.domain.dto.PostRequestDto;
import kr.ac.jejunu.myproject.domain.dto.PostUpdateDto;
import kr.ac.jejunu.myproject.repository.CategoryRepository;
import kr.ac.jejunu.myproject.repository.CommentRepository;
import kr.ac.jejunu.myproject.repository.PostRepository;
import kr.ac.jejunu.myproject.repository.TagRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FileService fileService;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    public Post getByPostId(Long id) {
        Post post = postRepository.findById(id).get();
        post.setViews(post.getViews() + 1);
        return post;
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> getTagPosts(Integer tag, Pageable pageable) {
        // return postRepository.findAllByTag(tag, pageable);
        return null;
    }

    public Page<Post> getPostsByCategoryId(Integer categoryId, Pageable pageable) {
        return postRepository.findAllByCategoryId(categoryId, pageable);
    }

    public Post savePost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto.getContent(), postRequestDto.getContentTitle());
        postRequestDto.getTags().forEach(tagId -> {
            new PostTag(post, tagRepository.findById(tagId).orElse(null));
        });
        post.setCategory(categoryRepository.findById(postRequestDto.getCategoryId()).get());
        post.setThumbnail(postRequestDto.getThumbnail());
        return postRepository.save(post);
    }

    public Post updatePost(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postUpdateDto.getId()).get();
        post.setContentTitle(postUpdateDto.getContentTitle());
        post.setContent(postUpdateDto.getContent());
        postUpdateDto.getTags().forEach(tagId -> {
            new PostTag(post, tagRepository.findById(tagId).orElse(null));
        });
        post.setThumbnail(postUpdateDto.getThumbnail());
        post.setCategory(categoryRepository.findById(postUpdateDto.getCategoryId()).orElse(null));

        if (postUpdateDto.getFileUrls() != null) {
            postUpdateDto.getFileUrls().forEach(fileUrl -> fileService.update(postUpdateDto.getId(), fileUrl));
        }
        return post;
    }

    public void deletePost(Long id) {
        try {
            postRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Post not found");
        }
    }

    public void likePost(Long id) {
        Post post = postRepository.findById(id).get();
        post.setLikes(post.getLikes() + 1);
    }

    public Long getLikes(Long id) {
        Post post = postRepository.findById(id).get();
        return post.getLikes();
    }

    public List<Post> getRecentPostsByAllTags() {
        List<Post> postList = new ArrayList<>();
        try {
            postList.add(postRepository.findTop1ByOrderByViewsDesc()); // 인기글 // 조회수 내림차순 첫번째
            postList.add(postRepository.findTop1ByOrderByIdDesc()); // 최신글 // 업로드 날짜 제일 빠른 게시글
            postList.addAll(postRepository
                    .findTop2ByCategoryOrderByCreatedDateDesc(categoryRepository.findByCategoryName("프로젝트")));
            postList.addAll(postRepository
                    .findTop2ByCategoryOrderByCreatedDateDesc(categoryRepository.findByCategoryName("TIL")));
            postList.addAll(postRepository
                    .findTop2ByCategoryOrderByCreatedDateDesc(categoryRepository.findByCategoryName("이슈해결")));

            // TODO n+1 해결
            postList = postList.stream()
                    .map(post -> {
                        List<Comment> comment = commentRepository.findAllByPostId(post.getId());
                        post.setCommentCount((long) comment.size());
                        return post;
                    }).toList();

        } catch (Exception e) {
            Post post = new Post("test", "testTitle");
            postRepository.save(post);
            postList.add(post);
        }

        return postList;
    }

}
