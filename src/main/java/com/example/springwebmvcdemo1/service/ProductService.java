package com.example.springwebmvcdemo1.service;

import com.example.springwebmvcdemo1.dto.ProductRequest;
import com.example.springwebmvcdemo1.dto.ProductResponse;
import com.example.springwebmvcdemo1.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProduct(String productName);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse findProductByID(int id);
    void deleteProduct(int ProductId);
    ProductResponse updateProduct(int id,ProductRequest productRequest);
}
