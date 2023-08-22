package kr.ac.jejunu.myproject.AuthUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    @PostMapping("/test")
    public String test(){
        return "LoginSuccess";
    }

    @GetMapping("/jwtTest")
    public String jwtTest(@RequestHeader("User-Agent") String userAgent){
        log.info("UserAgent = {}",userAgent);

        return userAgent;
    }
}
