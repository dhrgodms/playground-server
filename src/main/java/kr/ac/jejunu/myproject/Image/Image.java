package kr.ac.jejunu.myproject;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) // entity 저장되는 일시를 저장
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불필요한 기본 생성자를 만들지 않는다.
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postId;
    private String url;
}