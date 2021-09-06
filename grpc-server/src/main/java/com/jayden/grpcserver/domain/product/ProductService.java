package com.jayden.grpcserver.domain.product;

import com.jayden.grpcserver.support.error.NotFoundProductException;
import com.jayden.product.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long addProduct(Product product) {
        ProductEntity entity = new ProductEntity(product.getName(),
            product.getDescription(), product.getPrice());
        productRepository.save(entity);
        return entity.getId();
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
