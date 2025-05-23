package com.khaikin.qrest.category;

import com.khaikin.qrest.exception.ConflictException;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable @Positive Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable @Positive Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/with-image")
    public ResponseEntity<Category> createCategory(@RequestPart Category category,
                                                   @RequestPart MultipartFile imageFile,
                                                   HttpServletRequest request) {
        try {
            Category newCategory = categoryService.createCategory(category, imageFile, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ConflictException("Conflict Error: Create Category with Image");
        }
    }

    @PutMapping("/with-image/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestPart Category category,
                                           @RequestPart MultipartFile imageFile, HttpServletRequest request) {
        try {
            Category newCategory = categoryService.updateCategory(id, category, imageFile, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("category", "categoryId", id);
        } catch (Exception e) {
            throw new ConflictException("Conflict Error: Update category");
        }
    }
}
