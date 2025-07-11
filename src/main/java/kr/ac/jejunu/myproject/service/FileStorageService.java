package kr.ac.jejunu.myproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    public String storeFile(MultipartFile file) throws IOException {
        try {
            String key = "uploads/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            String fileUrl = "https://" + bucket + ".s3.amazonaws.com/" + key;
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}
