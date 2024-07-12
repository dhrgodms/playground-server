package kr.ac.jejunu.myproject.File;

import kr.ac.jejunu.myproject.Post.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") List<MultipartFile> files){
        List<String> filePaths = files.stream()
                .map(file->{
            try {
                return fileStorageService.storeFile(file);
            }catch(IOException e){
                throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
            }
        }).collect(Collectors.toList());
        return new ResponseEntity<>("File uploaded successfully: "+String.join(",",filePaths), HttpStatus.OK);

    }

}
