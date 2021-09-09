package com.jayden.client.config.grpc;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "grpc.server")
public class GrpcServerProperty {

    private final String address;
    private final Integer port;

    public GrpcServerProperty(String address, Integer port) {
        this.address = address;
        this.port = port;
    }
}
