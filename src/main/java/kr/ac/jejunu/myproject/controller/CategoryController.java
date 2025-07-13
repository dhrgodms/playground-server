package kr.ac.jejunu.myproject.controller;

import kr.ac.jejunu.myproject.domain.Category;
import kr.ac.jejunu.myproject.domain.dto.CategoryRequestDto;
import kr.ac.jejunu.myproject.domain.dto.CategoryResponseDto;
import kr.ac.jejunu.myproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll().stream().map(c -> new CategoryResponseDto(c)));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        Category category = new Category(categoryRequestDto.getCategoryName(),
                categoryRequestDto.getCategoryColor(),
                categoryRequestDto.getCategoryDescription(),
                categoryRequestDto.getCategoryPath());
        categoryRepository.save(category);
        return ResponseEntity.ok(new CategoryResponseDto(category));
    }

    @DeleteMapping("/{category_id}")
    public void deleteCategory(@PathVariable("category_id") Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
