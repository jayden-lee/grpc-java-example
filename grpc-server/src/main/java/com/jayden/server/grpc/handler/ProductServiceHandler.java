package com.jayden.server.grpc.handler;

import com.jayden.product.*;
import com.jayden.server.domain.product.ProductService;
import com.jayden.server.support.error.NotFoundProductException;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
public class ProductServiceHandler extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    public ProductServiceHandler(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<Product> responseObserver) {
        Product product = productService.addProduct(request.getProduct());
        responseObserver.onNext(product);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<Product> responseObserver) {
        Long productId = Long.valueOf(request.getProductId());

        try {
            Product product = productService.getProduct(productId);
            responseObserver.onNext(product);
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
