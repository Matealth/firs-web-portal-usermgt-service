package com.firs.wps.usermgt.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
@JsonDeserialize(as = ApiResponse.class)
public class ApiResponse {
    private String message;
    private Boolean success;
    private Object result;
    private HashMap<String, String> errors;

}
