package com.jayden.client.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {
    private final String name;
    private final String description;
    private final Float price;

    @Builder
    public Product(String name, String description, Float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
