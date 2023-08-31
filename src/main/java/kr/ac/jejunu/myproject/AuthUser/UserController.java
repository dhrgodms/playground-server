package kr.ac.jejunu.myproject.AuthUser;

import kr.ac.jejunu.myproject.Post.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    final String BIRTH = "001200";
    final String EMAIL = "aabbcc@gmail.com";
    final String NICKNAME = "침착맨";
    final Long SEQUENCEID = Long.valueOf(1);
    final User.Admin ADMIN = User.Admin.FALSE;

    User user = User.builder()
            .userEmail(EMAIL)
            .userBirth(BIRTH)
            .userNickname(NICKNAME)
            .admin(ADMIN)
            .userSequenceId(SEQUENCEID)
            .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
            .build();


    @PostMapping("/join")
    public String join(@RequestBody User user) {
        log.info("로그인 시도됨");

        userRepository.save(user);


        return user.toString();

    }

    // 로그인
    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> user, @RequestHeader("User-Agent") String userAgent){
        log.info("user email = {}", user.get("userEmail"));
        log.info("user agent = {}", userAgent);

        User member = userRepository.findByUserEmail(user.get("userEmail"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        Token tokenDto = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());

        jwtService.login(tokenDto, userAgent);

        return member;
    }

}
