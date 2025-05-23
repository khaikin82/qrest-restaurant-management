package com.khaikin.qrest.category;

import com.khaikin.qrest.food.Food;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    Category createCategory(Category category, MultipartFile imageFile, HttpServletRequest request)
            throws IOException;

    Category updateCategory(Long id, Category category, MultipartFile updateImageFile, HttpServletRequest request)
            throws IOException;

    void deleteCategory(Long id);
}
