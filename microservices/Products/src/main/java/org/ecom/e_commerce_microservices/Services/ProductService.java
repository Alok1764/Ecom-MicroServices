package org.ecom.e_commerce_microservices.Services;

import lombok.RequiredArgsConstructor;
import org.ecom.e_commerce_microservices.DTO.Request.ProductRequest;
import org.ecom.e_commerce_microservices.DTO.Response.ProductResponse;
import org.ecom.e_commerce_microservices.Entities.Product;
import org.ecom.e_commerce_microservices.Repositories.ProductRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product =new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct = productRepo.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        return ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .active(savedProduct.getActive())
                .category(savedProduct.getCategory())
                .description(savedProduct.getDescription())
                .price(savedProduct.getPrice())
                .imageUrl(savedProduct.getImageUrl())
                .stockQuantity(savedProduct.getStockQuantity())
                .build();

    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }


    public List<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepo.findByActiveTrue(pageable).getContent()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProducts(Pageable pageable, String keyword) {
        return productRepo.findByKeyword(pageable,keyword).getContent()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepo.findById(id)
                .map(existingProduct->{
                    updateProductFromRequest(existingProduct,productRequest);
                    Product savedProduct = productRepo.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }

    public boolean deleteProduct(Long id) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepo.save(product);
                    return true;
                }).orElse(false);
    }
}

