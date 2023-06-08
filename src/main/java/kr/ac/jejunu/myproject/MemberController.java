package kr.ac.jejunu.myproject;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/member")
@RequiredArgsConstructor // 생성자 자동으로 생성-> 자동으로 의존성 주입
public class MemberController {
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userService;
    @GetMapping("/{id}")
    public Member get(@PathVariable Long id){
        return memberDao.findById(id).get();
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal User user, Map<String, Object>model){
        List<Member> members = memberDao.findAll();
        model.put("members", members);
        model.put("currentMemberName", user.getUsername());
        return "homepage";
    }


//    @PostMapping("/upload")
//    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
//        File path = new File(request.getServletContext().getRealPath("/") + "/static/");
////        path.mkdir();
//        FileOutputStream fileOutputStream = new FileOutputStream(path + file.getOriginalFilename());
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//        bufferedOutputStream.write(file.getBytes());
//        bufferedOutputStream.close();
//
//        return "http://localhost:8080/" + file.getOriginalFilename();
//    }

    @GetMapping("/admin")
    public String adminPage(@AuthenticationPrincipal User user, Map<String, Object> model){
        model.put("currentAdminId", user.getUsername());
        return "adminpage";
    }

    @GetMapping("/user/join")
    public String memberJoinForm(Member memberForm){
        return "memberJoinForm";
    }

    @PostMapping("/user/join")
    public String memberJoin(Member memberForm){
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        memberDao.save(memberForm);
        System.out.println("memberForm" + memberForm);
        return "redirect:/api/member/main";
    }

    @GetMapping("/login")
    public String login(){
        return "loginpage";
    }
}