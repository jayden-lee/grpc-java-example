package com.jayden.client.config.grpc;

import com.jayden.product.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    private GrpcServerProperty grpcServerProperty;

    public GrpcConfig(GrpcServerProperty grpcServerProperty) {
        this.grpcServerProperty = grpcServerProperty;
    }

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder
            .forAddress(grpcServerProperty.getAddress(), grpcServerProperty.getPort())
            .usePlaintext()
            .build();
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub(ManagedChannel managedChannel) {
        return ProductServiceGrpc.newBlockingStub(managedChannel);
    }
}
