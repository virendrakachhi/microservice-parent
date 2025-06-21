package com.product_service.controller;


import com.product_service.dto.ProductRequest;
import com.product_service.dto.ProductResponse;
import com.product_service.model.Product;
import com.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private ProductService service;

    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest product) {
        service.saveProduct(product);
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "Testing";
    }
}
