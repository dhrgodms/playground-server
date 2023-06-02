package kr.ac.jejunu.myproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) // entity 저장되는 일시를 저장
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @CreatedDate // 등록된 일시 자동 저장
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"comments"})
    private User user;
    @Version // 수정하면 버전이 바뀜
    private Long version;
}
