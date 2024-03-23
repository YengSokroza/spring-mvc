package com.example.springwebmvcdemo1.repository;

import com.example.springwebmvcdemo1.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> allProducts = new ArrayList<>(){{
        add(Product.builder()
                .id(1)
                .title("one")
                .description("this is the product one description")
                .price(3.5f)
                .imageUrl("product1.jpg")
                .build()

        );

        add(Product.builder()
                .id(2)
                .title("two")
                .description("this is the product two description")
                .price(3.5f)
                .imageUrl("product2.jpg")
                .build()

        );

        add(Product.builder()
                .id(3)
                .title("three")
                .description("this is the product three description")
                .price(3.5f)
                .imageUrl("product3.jpg")
                .build()

        );
    }};

    public List<Product> getAllProducts(){
        return allProducts;
    }

    public void addProduct(Product product){
        allProducts.add(product);
    }

    public void updateProduct(Product product){
        // need find the index of the product
        int index =   allProducts.indexOf(
                allProducts.stream()
                        .filter(pro->pro.getId()==product.getId())
                        .findFirst()
                        .orElse(null)
        ) ;
        allProducts.set(index,product);
    }
    public void deleteProduct(int id){
        int index =   allProducts.indexOf(
                allProducts.stream()
                        .filter(pro->pro.getId()==id)
                        .findFirst()
                        .orElse(null)
        ) ;
        allProducts.remove(index);
    }

}
