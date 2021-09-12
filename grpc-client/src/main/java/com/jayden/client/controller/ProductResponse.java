package com.jayden.client.controller;

import com.jayden.client.domain.ProductResult;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private final String name;
    private final String description;
    private final Float price;

    @Builder
    public ProductResponse(ProductResult product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
