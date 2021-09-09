package com.jayden.client.domain;

import com.jayden.product.GetProductRequest;
import com.jayden.product.GetProductResponse;
import com.jayden.product.ProductServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    public Product getProduct(Long id) {
        GetProductRequest request = GetProductRequest.newBuilder()
            .setProductId(String.valueOf(id))
            .build();

        GetProductResponse response = productServiceBlockingStub
            .withDeadlineAfter(1000, TimeUnit.MILLISECONDS)
            .getProduct(request);

        return Product.builder()
            .name(response.getProduct().getName())
            .description(response.getProduct().getDescription())
            .price(response.getProduct().getPrice())
            .build();
    }
}
