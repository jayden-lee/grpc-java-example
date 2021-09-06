package com.jayden.grpcserver.grpc.handler;

import com.jayden.grpcserver.domain.product.ProductService;
import com.jayden.grpcserver.support.error.NotFoundProductException;
import com.jayden.product.*;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@GRpcService
public class ProductServiceHandler extends ProductServiceGrpc.ProductServiceImplBase {

    private final Logger log = LoggerFactory.getLogger(ProductServiceHandler.class);

    private final ProductService productService;

    public ProductServiceHandler(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void addProduct(CreateProductRequest request, StreamObserver<CreateProductResponse> responseObserver) {
        Long productId = productService.addProduct(request.getProduct());

        CreateProductResponse response = CreateProductResponse.newBuilder()
            .setProductId(String.valueOf(productId))
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<GetProductResponse> responseObserver) {
        Long productId = Long.valueOf(request.getProductId());

        try {
            Product product = productService.getProduct(productId);
            GetProductResponse response = GetProductResponse.newBuilder()
                .setProduct(product)
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NotFoundProductException nfe) {
            log.info("Not found product error", nfe);
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        } catch (Exception e) {
            log.info("Get product error", e);
            responseObserver.onError(new StatusException(Status.INTERNAL));
        }
    }
}
