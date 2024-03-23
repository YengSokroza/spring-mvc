package com.example.springwebmvcdemo1.service.serviceimpl;

import com.example.springwebmvcdemo1.dto.CategoryRequest;
import com.example.springwebmvcdemo1.dto.CategoryResponse;
import com.example.springwebmvcdemo1.dto.ProductRequest;
import com.example.springwebmvcdemo1.dto.ProductResponse;
import com.example.springwebmvcdemo1.model.Category;
import com.example.springwebmvcdemo1.model.Product;
import com.example.springwebmvcdemo1.repository.CategoryRespository;
import com.example.springwebmvcdemo1.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRespository categoryRespository;

    private CategoryResponse mapCategoryToRespond(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    private Category mapRequestToCategory(CategoryRequest request){
        return Category.builder()
                .title(request.title())
                .description(request.description())
                .build();
    }

    @Override
    public List<CategoryResponse> getALlCategory(String categoryTitle) {
        var category = categoryRespository.getAllCategories();
        if(!categoryTitle.isEmpty()){
            category = category.stream()
                    .filter(c -> c.getTitle().toLowerCase().contains(categoryTitle))
                    .toList();
        }
        return category.stream()
                .map(this::mapCategoryToRespond).toList();
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category newCategory = mapRequestToCategory(request);
        //find max id
        var maxID = categoryRespository.getAllCategories()
                .stream()
                .max(Comparator.comparingInt(Category::getId))
                .map(Category::getId);
        int newID=1;
        if(maxID.isPresent()){
            newID = maxID.get() + 1;
        }
        newCategory.setId(newID);
        categoryRespository.addCategory(newCategory);
        return mapCategoryToRespond(newCategory);
    }

    @Override
    public CategoryResponse findCategoryByID(int id) {
        Category category = categoryRespository.getAllCategories().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"Category doesn't exist"));
        return mapCategoryToRespond(category);
    }

    private Category searchCategoryByID(int id){
        return  categoryRespository.getAllCategories()
                .stream().filter(c->c.getId()==id)
                .findFirst()
                .orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND,"Category doesn't exist!!"));
    }

    @Override
    public void deleteCategory(int categoryid) {
        categoryRespository.deleteCategory(searchCategoryByID(categoryid).getId());
    }

    @Override
    public CategoryResponse updateCategory(int id, CategoryRequest categoryRequest) {
        // find if the product exist
        var result = searchCategoryByID(id);
        result= mapRequestToCategory(categoryRequest);
        result.setId(id);
        categoryRespository.updateCategory(result);
        return mapCategoryToRespond(result);
    }


}
