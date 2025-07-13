package kr.ac.jejunu.myproject.controller;

import kr.ac.jejunu.myproject.domain.Tag;
import kr.ac.jejunu.myproject.domain.dto.TagRequestDto;
import kr.ac.jejunu.myproject.domain.dto.TagResponseDto;
import kr.ac.jejunu.myproject.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody TagRequestDto tagRequestDto) {
        Tag tag = new Tag(tagRequestDto.getTagName(), tagRequestDto.getTagColor(), tagRequestDto.getTagDescription());
        tagRepository.save(tag);
        return ResponseEntity.ok(new TagResponseDto(tag));
    }

    @DeleteMapping("/{tagId}")
    public void deleteTag(@PathVariable("tagId") Long tagId) {
        tagRepository.deleteById(tagId);
    }
}
