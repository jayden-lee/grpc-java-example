package com.jayden.server.domain.product;

import com.jayden.server.support.error.NotFoundProductException;
import com.jayden.product.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        ProductEntity entity = ProductEntity.builder()
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();

        productRepository.save(entity);

        return Product.newBuilder()
            .setName(entity.getName())
            .setDescription(entity.getDescription())
            .setPrice(entity.getPrice())
            .build();
    }

    public Product getProduct(Long productId) {
        ProductEntity entity = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundProductException("Not Found Product"));

        return Product.newBuilder()
            .setName(entity.getName())
            .setDescription(entity.getDescription())
            .setPrice(entity.getPrice())
            .build();
    }
}
