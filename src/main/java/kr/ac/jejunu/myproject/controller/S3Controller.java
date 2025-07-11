package kr.ac.jejunu.myproject.controller;

import kr.ac.jejunu.myproject.service.S3PresignedUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3PresignedUrlService s3PresignedUrlService;

    @GetMapping("/presigned-url")
    public ResponseEntity<String> getPresignedUrl(@RequestParam String fileName) {
        String url = s3PresignedUrlService.generatePresignedUrl(fileName);
        return ResponseEntity.ok(url);
    }
}