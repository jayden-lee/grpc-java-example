package com.jayden.client.support.api;

import com.jayden.client.support.error.ErrorType;
import com.jayden.client.support.error.ErrorData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final ApiResultType result;
    private final T data;
    private final ErrorData error;

    public static <T> ApiResponse success() {
        return new ApiResponse(ApiResultType.SUCCESS, null, null);
    }

    public static <T> ApiResponse success(T data) {
        return new ApiResponse(ApiResultType.SUCCESS, data, null);
    }

    public static <T> ApiResponse error(ErrorType errorType) {
        return new ApiResponse(ApiResultType.ERROR, null, new ErrorData(errorType));
    }
}
