//package kr.ac.jejunu.myproject.Member;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import kr.ac.jejunu.myproject.Comment.Comment;
//import lombok.*;
//
//import java.util.List;
//
//@Data
//@Entity(name = "userinfo") // 해당 이름의 table을 활용하게 된다.
//@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불필요한 기본 생성자를 만들지 않는다.
//@ToString
//public class Member {
//    @Id // primary key
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
//    private Long id;
//    private String name;
//    private String password;
//    @JsonIgnoreProperties({"member"}) // member정보는 무시하고 가져와
//    @OneToMany(mappedBy = "member") // comment에 있는 member정보로 매핑해줘
//    private List<Comment> comments;
//
//    public Member(String name, String password){
//        this.name = name;
//        this.password = password;
//    }
//
//    public void setPassword(String password){
//        this.password = password;
//    }
//}