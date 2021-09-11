package com.jayden.client.config.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

@Slf4j
public class GrpcLogClientInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions,
                                                               Channel next) {

        log.info("Received call to {} method", method.getFullMethodName());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void sendMessage(ReqT message) {
                log.debug("Request message: {}", message);
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                super.start(
                    new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                        @Override
                        public void onMessage(RespT message) {
                            log.debug("Response message: {}", message);
                            super.onMessage(message);
                        }

                        @Override
                        public void onHeaders(Metadata headers) {
                            log.debug("gRPC headers: {}", headers);
                            super.onHeaders(headers);
                        }

                        @Override
                        public void onClose(Status status, Metadata trailers) {
                            stopWatch.stop();
                            log.info("Interaction ends with status: {}, trailers: {}, elapsed: {}ms", status.getCode(), trailers, stopWatch.getTotalTimeMillis());
                            super.onClose(status, trailers);
                        }
                    }, headers);
            }
        };
    }
}
