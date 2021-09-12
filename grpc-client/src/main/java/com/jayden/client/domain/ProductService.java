package com.jayden.client.domain;

import com.jayden.product.GetProductRequest;
import com.jayden.product.Product;
import com.jayden.product.ProductServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    public ProductResult getProduct(Long id) {
        GetProductRequest request = GetProductRequest.newBuilder()
            .setProductId(String.valueOf(id))
            .build();

        Product product = productServiceBlockingStub
            .withDeadlineAfter(1000, TimeUnit.MILLISECONDS)
            .getProduct(request);

        return ProductResult.builder()
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();
    }
}
