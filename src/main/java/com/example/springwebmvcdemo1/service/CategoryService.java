package com.example.springwebmvcdemo1.service;

import com.example.springwebmvcdemo1.dto.CategoryRequest;
import com.example.springwebmvcdemo1.dto.CategoryResponse;

import java.util.List;


public interface CategoryService {
    List<CategoryResponse> getALlCategory(String title);
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse findCategoryByID(int Categoryid);
    void deleteCategory(int Categoryid);
    CategoryResponse updateCategory(int id,CategoryRequest categoryRequest);

}
