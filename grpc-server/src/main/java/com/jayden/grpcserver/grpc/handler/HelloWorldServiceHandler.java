package com.jayden.grpcserver.grpc.handler;

import com.jayden.helloworld.HelloReply;
import com.jayden.helloworld.HelloRequest;
import com.jayden.helloworld.HelloWorldGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class HelloWorldServiceHandler extends HelloWorldGrpc.HelloWorldImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
            .setMessage("hello " + request.getName())
            .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
