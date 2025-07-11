package kr.ac.jejunu.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.jejunu.myproject.domain.FileEntity;
import kr.ac.jejunu.myproject.repository.FileDao;
import kr.ac.jejunu.myproject.service.FileStorageService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/files")
public class FileUploadController {

    private final FileStorageService fileStorageService;
    private final FileDao fileDao;

    // 단일 파일 업로드
    @PostMapping
    public ResponseEntity<String> uploadSingleFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileStorageService.storeFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to store file " + file.getOriginalFilename());
        }
    }

    // 다중 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        List<String> filePaths = files.stream()
                .map(file -> {
                    try {
                        return fileStorageService.storeFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store file " +
                                file.getOriginalFilename(), e);
                    }
                }).collect(Collectors.toList());
        return new ResponseEntity<>(String.join(",", filePaths), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public List<FileEntity> getAll(@PathVariable Long id) {
        return fileDao.findByPostId(id);
    }
}
