package com.product_service.service;

import com.product_service.dto.ProductRequest;
import com.product_service.dto.ProductResponse;
import com.product_service.model.Product;
import com.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {


    @Autowired
    ProductRepository repository;


    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream().map(p ->
                new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getDescription())
        ).collect(Collectors.toList());
    }

    public void saveProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        repository.save(product);
        log.info("Product is saved" + product.getId());

    }
}
