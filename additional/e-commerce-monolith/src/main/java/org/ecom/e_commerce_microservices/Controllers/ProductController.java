package org.ecom.e_commerce_microservices.Controllers;


import lombok.RequiredArgsConstructor;
import org.ecom.e_commerce_microservices.DTO.Request.ProductRequest;
import org.ecom.e_commerce_microservices.DTO.Response.ProductResponse;
import org.ecom.e_commerce_microservices.Services.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam(required = false,defaultValue = "0") int pageNo,
                                                             @RequestParam(required = false,defaultValue = "10") int pageSize,
                                                             @RequestParam(required = false,defaultValue = "id") String sortedBy,
                                                             @RequestParam(required = false,defaultValue = "ASC") String sortOrder) {
        Sort sort=null;
        if(sortOrder.equalsIgnoreCase("ASC")) sort= Sort.by(sortedBy).ascending();
        else sort=Sort.by(sortedBy).descending();

        return ResponseEntity.ok(productService.getAllProducts(PageRequest.of(pageNo,pageSize,sort)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam(required = false,defaultValue = "0") int pageNo,
                                                                @RequestParam(required = false,defaultValue = "10") int pageSize,
                                                                @RequestParam(required = false,defaultValue = "id") String sortedBy,
                                                                @RequestParam(required = false,defaultValue = "ASC") String sortOrder,
                                                                @RequestParam String keyword) {
        Sort sort=null;
        if(sortOrder.equalsIgnoreCase("ASC")) sort= Sort.by(sortedBy).ascending();
        else sort=Sort.by(sortedBy).descending();
        return ResponseEntity.ok(productService.searchProducts(PageRequest.of(pageNo,pageSize,sort),keyword));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),
                HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }



}
