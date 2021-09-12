package com.jayden.client.config.grpc;

import brave.Tracing;
import brave.grpc.GrpcTracing;
import com.jayden.product.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    private final GrpcServerProperty grpcServerProperty;

    public GrpcConfig(GrpcServerProperty grpcServerProperty) {
        this.grpcServerProperty = grpcServerProperty;
    }

    @Bean
    public GrpcTracing grpcTracing(Tracing tracing) {
        return GrpcTracing.create(tracing);
    }

    @Bean
    public GrpcLogClientInterceptor grpcLogClientInterceptor() {
        return new GrpcLogClientInterceptor();
    }

    @Bean
    public ManagedChannel managedChannel(GrpcTracing grpcTracing, GrpcLogClientInterceptor grpcLogClientInterceptor) {
        return ManagedChannelBuilder
            .forAddress(grpcServerProperty.getAddress(), grpcServerProperty.getPort())
            .intercept(
                grpcTracing.newClientInterceptor(),
                grpcLogClientInterceptor
            )
            .usePlaintext()
            .build();
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub(ManagedChannel managedChannel) {
        return ProductServiceGrpc.newBlockingStub(managedChannel);
    }
}
