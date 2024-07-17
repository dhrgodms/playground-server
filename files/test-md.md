#issues 2

#error 240712 16:08 : DB에 file 경로는 올라가지만 file은 서버에 업로드되지 않는 상태
```java
@PostMapping("/add")  
public List<String> upload(@RequestParam("file") List<MultipartFile> files, HttpServletRequest request) throws IOException { 

	List<String> uploadedFilesUrls = new ArrayList<>();  
	System.out.println("files : "+files);
	
	// 현재 서블릿이 돌아가는 디렉토리의 경로 + "/static/"
	File path = new File(request.getServletContext().getRealPath("/") + "/static/");  
	
	// List<MultipartFile> files : 선택한 file들의 List
	for (MultipartFile file : files) {  
		// 현재 서블릿 경로 + /static/ + 파일 이름
		FileOutputStream fileOutputStream = new FileOutputStream(path + file.getOriginalFilename());  
		// 버퍼 스트림 이용하여 효율 증가
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);  
		bufferedOutputStream.write(file.getBytes());  
		bufferedOutputStream.close();  
		
		// url을 담아두는 array에 새로 만든 경로를 추가
		uploadedFilesUrls.add(hostname+":8080/images/" + file.getOriginalFilename());  
	}  

	// array에 저장해둔 경로를 하나씩 꺼내면서 Image를 생성하여 url과 id를 저장
	for (String url : uploadedFilesUrls){  
		Long postId = postDao.findTop1ByOrderByIdDesc().getId();  
		Image image = new Image();  
		image.setUrl(url);  
		image.setPostId(postId+1);  
		imageDao.save(image);  
	};  
	return uploadedFilesUrls;  
}
```

- httpServletRequest.getServletContext().getRealPath("/")
	- 현재 서버가 돌아가고 있는 서블릿의 경로
- BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);  
	- BufferedOutputStream : byte형태로 파일을 기록할 때 사용하는 버퍼 스트림
	- 버퍼 스트림 : 데이터를 출력할 때 파일의 데이터나 코드를 그대로 읽어 사용하는 것이 아닌, 버퍼에 임시 저장하여 모아 두었다가 일정량만큼 모아지면 디스크에 write 하는 방식
		=> 효율이 증가. (자잘한거 반복 안해도 됨)


> 기존에는 url만 db에 저장하는 방식이었구나

# 해결
## 1. fileDao 생성 (/File/FileDao.java)
```java
package kr.ac.jejunu.myproject.File;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDao extends JpaRepository<FileEntity,Long> {
}

```
## 2. 파일 업로드 컨트롤러 작성 (/File/FileUploadController.java)
```java
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

```

## 3. 파일 저장 서비스 작성(/File/FileStorageService.java)

```java
package kr.ac.jejunu.myproject.File;

import kr.ac.jejunu.myproject.Post.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;



    @Autowired
    private FileDao fileDao;
    @Autowired
    private PostDao postDao;

    public String storeFile(MultipartFile file) throws IOException {
        // Ensure upload directory exists
        Path uploadPath = Paths.get(uploadDir+ File.separator+file.getOriginalFilename());

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Copy file to the target location (replacing existing file with the same name)
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

        // Save file path to database
        saveFilePathToDatabase(uploadPath.toString());

        return uploadPath.toString();
    }

    private void saveFilePathToDatabase(String filePath) {
        Long postId = postDao.findTop1ByOrderByIdDesc().getId();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilePath(filePath);
        // Ensure fileRepository is autowired or instantiated
        fileEntity.setPostId(postId);
        fileDao.save(fileEntity);
    }
}

```

## 4. fileEntity 작성(/File/FileEntity.java)
@Data 어노테이션을 붙여주면 여기에 작성하는대로 데이터베이스 내 테이블이 구성된다.
```java
package kr.ac.jejunu.myproject.File;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private Long postId;
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

```

## internal server error(500) 발생

#error 프론트에서는 file을 인지하지만 server에서 내부적인 에러 발생(500)
![[Pasted image 20240712192338.png]]
- server에서 파일을 저장할 폴더를 만들지 못하는 것으로 추정
- 아래 쉘 명령문으로 디렉토리를 생성하고 쓰기 권한 주기
```shell
sudo mkdir -p path/to/upload/directory
sudo chmod -R 755 path/to/upload/directory
```

하고나니까 해당 경로에 디렉토리가 있다는 것은 인식하게 되었다.
그럼에도 그 뒤에 copy는 안되는 이슈 발생...


경로가 잘못된 것 같아 upload 하는 path를 설정하는 라인을 고쳐보았다.
```java
Path uploadPath = Paths.get(uploadDir+File.separator+file.getOriginalFilename());
```
- File.sepatator : OS마다 파일구조 구분자가 다른데, 이 함수를 통해 OS별로 경로를 알맞게 이어붙일 수 있게 해준다.


그랬더니 업로드가 가능해졌다.


이제 할 일은 게시글 별로 파일 경로를 가져와 프론트엔드에서 띄워주는 것이다.
이슈 추가하자~


## 도움이 된 정보들
- https://fruitdev.tistory.com/169
- https://sjh9708.tistory.com/94