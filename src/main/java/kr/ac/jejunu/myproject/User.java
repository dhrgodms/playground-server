package kr.ac.jejunu.myproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "userinfo") // 해당 이름의 table을 활용하게 된다.
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;
    private String name;
    private String password;
    @JsonIgnoreProperties({"user"}) // user정보는 무시하고 가져와
    @OneToMany(mappedBy = "user") // comment에 있는 user정보로 매핑해줘
    private List<Comment> comments;
}
