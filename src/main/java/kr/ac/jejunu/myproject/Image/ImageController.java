package kr.ac.jejunu.myproject.Image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ImageController {
    private final ImageDao imageDao;

    @Autowired
    private ImageStorageService imageStorageService;


    @GetMapping("/{id}")
    public Image get(@PathVariable Long id) {
        return imageDao.findById(id).get();
    }


    @PostMapping("/add")
    public ResponseEntity<String> upload(@RequestParam("file") List<MultipartFile> files) {
        List<String> filePaths = files.stream()
                .map(file -> {
                    try {
                        return imageStorageService.storeImage(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
                    }
                }).collect(Collectors.toList());
        return new ResponseEntity<>("File uploaded successfully: " + String.join(",", filePaths), HttpStatus.OK);

    }

    @GetMapping("/all/{id}")
    public List<Image> getAll(@PathVariable Long id) {
        return imageDao.findByPostId(id);
    }


}