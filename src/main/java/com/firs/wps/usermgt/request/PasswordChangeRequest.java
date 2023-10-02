package com.firs.wps.usermgt.request;

import com.firs.wps.usermgt.annotation.ValidPassword;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {

    @JsonProperty("username")
    @NotBlank(message = "Username address is required")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password can not be null")
    @ValidPassword(message = "Enter valid password")
    private String password;

    @JsonProperty("confirm_password")
    private String passwordConfirm;

    private String otp;
}
