package com.jayden.grpcserver.grpc.interceptor;

import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@GRpcGlobalInterceptor
public class RequestLogServerInterceptor implements ServerInterceptor {

    private final Logger log = LoggerFactory.getLogger(RequestLogServerInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        Context context = Context.current();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String methodName = call.getMethodDescriptor().getFullMethodName();

        return Contexts.interceptCall(
            context,
            new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                @Override
                public void close(Status status, Metadata metadata) {
                    stopWatch.stop();
                    log.info("Access rpc methodName: {}, elapsed: {}ms", methodName, stopWatch.getTotalTimeMillis());
                    super.close(status, metadata);
                }
            }, headers, next);
    }
}
