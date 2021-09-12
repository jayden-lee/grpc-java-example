package com.jayden.client.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResult {
    private final String name;
    private final String description;
    private final Float price;

    @Builder
    public ProductResult(String name, String description, Float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
