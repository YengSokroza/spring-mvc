package com.example.springwebmvcdemo1.repository;

import com.example.springwebmvcdemo1.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRespository {
    List<Category> allCategories = new ArrayList<>(){{
        add(Category.builder()
                .id(1)
                .title("clothes")
                .description("Women's clothes.")
                .build()
        );
        add(Category.builder()
                .id(2)
                .title("bag")
                .description("Bag for both men and women.")
                .build()
        );add(Category.builder()
                .id(3)
                .title("Jacket")
                .description("Men's Jacket")
                .build()
        );

    }};

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public void addCategory(Category category){
        allCategories.add(category);
    }

    public void updateCategory(Category category){
        int index = allCategories.indexOf(
                allCategories.stream()
                        .filter(c -> c.getId() == category.getId())
                        .findFirst()
                        .orElse(null)
        );
        allCategories.set(index,category);
    }

    public void deleteCategory(int id){
        int index = allCategories.indexOf(
                allCategories.stream()
                        .filter(c -> c.getId()==id)
                        .findFirst()
                        .orElse(null)
        );
        allCategories.remove(index);
    }
}
