package com.firs.wps.usermgt.util;

import com.firs.wps.usermgt.response.ApiResponse;

import java.util.HashMap;

public class ApiUtil {

    public static ApiResponse prepareResponse(Object response) {
        return  ApiResponse.builder()
                .success(true)
                .result(response)
                .build();
    }

    public static ApiResponse prepareResponse(String message, boolean result) {
        return  ApiResponse.builder()
                .message(message)
                .success(result)
                .build();
    }
    public static ApiResponse prepareResponse(Object response, String message) {
        return  ApiResponse.builder()
                .message(message)
                .success(true)
                .result(response)
                .build();
    }

    public static ApiResponse prepareError(Exception exception) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("error", exception.getLocalizedMessage());
        return  ApiResponse.builder()
                .errors(errors)
                .success(false)
                .build();
    }
}
