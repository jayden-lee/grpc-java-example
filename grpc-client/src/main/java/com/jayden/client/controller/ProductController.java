package com.jayden.client.controller;

import com.jayden.client.domain.ProductResult;
import com.jayden.client.domain.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        log.info("getProduct id: {}", id);
        ProductResult product = productService.getProduct(id);
        return new ProductResponse(product);
    }
}
