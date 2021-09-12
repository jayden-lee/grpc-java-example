package com.jayden.server.grpc.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.util.StopWatch;

@Slf4j
@GRpcGlobalInterceptor
public class GrpcLogServerInterceptor implements ServerInterceptor {

    private static final Metadata.Key<String> CUSTOM_HEADER_KEY =
        Metadata.Key.of("custom_client_header_key", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {

        log.info("Received headers from client: {}", headers);

        Context context = Context.current();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String methodName = call.getMethodDescriptor().getBareMethodName();

        return Contexts.interceptCall(
            context,
            new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                @Override
                public void close(Status status, Metadata metadata) {
                    stopWatch.stop();
                    log.info("Access rpc method: {}, status: {}, elapsed: {}ms", methodName, status.getCode().name(), stopWatch.getTotalTimeMillis());
                    super.close(status, metadata);
                }
            }, headers, next);
    }
}
