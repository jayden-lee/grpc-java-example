package com.jayden.server.grpc.handler;

import com.jayden.product.HealthCheckRequest;
import com.jayden.product.HealthCheckResponse;
import com.jayden.product.HealthServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class HealthServiceHandler extends HealthServiceGrpc.HealthServiceImplBase {

    @Override
    public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        HealthCheckResponse response = HealthCheckResponse.getDefaultInstance();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
