package com.example.springwebmvcdemo1.controller;


import com.example.springwebmvcdemo1.dto.ProductRequest;
import com.example.springwebmvcdemo1.dto.ProductResponse;
import com.example.springwebmvcdemo1.service.ProductService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {
    @Autowired
    public ProductService productService;

//    @PostMapping("/create-product")
//    public Map<String, Object>

    private Map<String,Object> response(Object object,String message, int status){
        HashMap<String, Object> response = new HashMap<>();
        response.put("payload",object);
        response.put("message",message);
        response.put("status",status);
        return response;
    }


    //can get all product or filter search like ?productName=coca
    @GetMapping("/get-all")
    public Map<String, Object> getAllProducts(@RequestParam(defaultValue = "") String productName){
        return response(productService.getAllProduct(productName),
                "Retrieved data successfully"
                ,HttpStatus.OK.value());
    }

    @PostMapping("/new-product")
    public Map<String ,Object> createNewProduct(@RequestBody ProductRequest request){
        return response(productService.createProduct(request),
                "Created new product successfully"
                    ,HttpStatus.CREATED.value());
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public Map<String,Object> findProductByID(@PathVariable int id){
        return response(productService.findProductByID(id),
                "successfully retrieved the record"
                ,HttpStatus.FOUND.value());
    }

    @PatchMapping("/{id}")
    public Map<String, Object> updateProduct(@PathVariable int id , @RequestBody ProductRequest request) {
        return response(
                productService.updateProduct(id, request),
                "Update Product Successfully",
                HttpStatus.OK.value()
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return response(new ArrayList<>(),"Delete Successfully",HttpStatus.OK.value());
    }





}
