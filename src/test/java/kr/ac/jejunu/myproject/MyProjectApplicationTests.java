package kr.ac.jejunu.myproject;

import kr.ac.jejunu.myproject.Post.Post;
import kr.ac.jejunu.myproject.Post.PostDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
//private Long id;
//private String content;
//private String contentTitle;
//@CreatedDate // 등록된 일시 자동 저장
//private LocalDateTime createdDate;
//@LastModifiedDate
//private LocalDateTime modifiedDate;
//@ManyToOne(cascade = CascadeType.ALL)
//@JsonIgnoreProperties({"posts"})
//private Member member;
//@Version // 수정하면 버전이 바뀜
//private Long version;

//@SpringBootTest
//class MyProjectApplicationTests {
//    private PostDao postDao;
////
//    @Test
//    void contextLoads() {
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("kr.ac.jejunu.myproject");
//        postDao = applicationContext.getBean("postDao", PostDao.class);
//    }

//    @Test
//    void deletePost(){
//        Post post = new Post();
//        post.setId(1L);
//        post.setContent("test");
//        post.setContentTitle("testTitle");
//        postDao.save(post);
//
//
//        postDao.deleteById(1L);
//    }

//}
