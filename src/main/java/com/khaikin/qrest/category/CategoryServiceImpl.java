package com.khaikin.qrest.category;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.util.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;
    private final String uploadDir = "images/category";


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setImageUrl(category.getImageUrl());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public Category createCategory(Category category, MultipartFile imageFile, HttpServletRequest request)
            throws IOException {
        String imageUrl = fileStorageService.storeFile(imageFile, uploadDir, request);
        category.setImageUrl(imageUrl);

        return categoryRepository.save(category);
    }


    @Override
    public Category updateCategory(Long id, Category category, MultipartFile updateImageFile, HttpServletRequest request)
            throws IOException {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setImageUrl(category.getImageUrl());

        String imageUrl = fileStorageService.storeFile(updateImageFile, uploadDir, request);
        existingCategory.setImageUrl(imageUrl);

        return categoryRepository.save(existingCategory);

    }

    @Override
    public void deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
        categoryRepository.delete(existingCategory);
    }
}