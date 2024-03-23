package com.example.springwebmvcdemo1.controller;

import com.example.springwebmvcdemo1.dto.CategoryRequest;
import com.example.springwebmvcdemo1.dto.ProductRequest;
import com.example.springwebmvcdemo1.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Primary
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;

    private Map<String,Object> response(Object object, String message, int status){
        HashMap<String, Object> response = new HashMap<>();
        response.put("payload",object);
        response.put("message",message);
        response.put("status",status);
        return response;
    }

    @GetMapping("/get-all")
    public Map<String ,Object> getAllCategories(@RequestParam(defaultValue = "") String title){
        return response(categoryService.getALlCategory(title),
                "Retrieve category successfully!",
                HttpStatus.OK.value()
        );
    }

    @PostMapping("/new-category")
    public Map<String ,Object> createNewCategory(@RequestBody CategoryRequest request){
        return response(categoryService.createCategory(request),
                "Created new category successfully"
                ,HttpStatus.CREATED.value());
    }

    @GetMapping("/{id}")
    public Map<String,Object> findCategoryByID(@PathVariable int id){
        return response(categoryService.findCategoryByID(id) ,
                "successfully retrieved the record"
                ,HttpStatus.FOUND.value());
    }

    @PatchMapping("/{id}")
    public Map<String, Object> updateCategory(@PathVariable int id , @RequestBody CategoryRequest request) {
        return response(
                categoryService.updateCategory(id, request),
                "Update category Successfully",
                HttpStatus.OK.value()
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        return response(new ArrayList<>(),"Delete Successfully",HttpStatus.OK.value());
    }
}
