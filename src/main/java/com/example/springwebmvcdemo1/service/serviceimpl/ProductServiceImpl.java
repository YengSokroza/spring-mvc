package com.example.springwebmvcdemo1.service.serviceimpl;

import com.example.springwebmvcdemo1.dto.ProductRequest;
import com.example.springwebmvcdemo1.dto.ProductResponse;
import com.example.springwebmvcdemo1.model.Product;
import com.example.springwebmvcdemo1.repository.ProductRepository;
import com.example.springwebmvcdemo1.service.ProductService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    private ProductResponse mapProductToRespond(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .description(product.getDescription())
                .build();
    }

    private Product mapRequestToProduct(ProductRequest request){
        return Product.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .imageUrl(request.imageUrl())
                .build();
    }


    @Override
    public List<ProductResponse> getAllProduct(String productName) {
        var product = productRepository.getAllProducts();
        if (!productName.isEmpty()){
            product = product.stream().filter(
                    pro-> pro.getTitle().toLowerCase().contains(productName.toLowerCase())
            ).toList();
        }
        return  product
                .stream()
                .map(this::mapProductToRespond).toList();

    }



    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product newProduct = mapRequestToProduct(request);
            //find max id
        var maxID = productRepository.getAllProducts()
                .stream()
                .max(Comparator.comparingInt(Product::getId))
                .map(Product::getId);
        int newID=1;
        if(maxID.isPresent()){
            newID = maxID.get() + 1;
        }
        newProduct.setId(newID);
        productRepository.addProduct(newProduct);
        return mapProductToRespond(newProduct);
    }

    @Override
    public ProductResponse findProductByID(int id) {
        Product product = productRepository.getAllProducts().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"Product doesn't exist"));
        return mapProductToRespond(product);
    }



    private Product searchProductByID(int id){
        return  productRepository.getAllProducts()
                .stream().filter(p->p.getId()==id)
                .findFirst()
                .orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND,"Product doesn't exist!!"));
    }


    @Override
    public ProductResponse updateProduct(int id , ProductRequest productRequest) {
        // find if the product exist
        var result = searchProductByID(id);
        result= mapRequestToProduct(productRequest);
        result.setId(id);
        productRepository.updateProduct(result);
        return mapProductToRespond(result);
    }


    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteProduct(searchProductByID(productId).getId());

    }


}
