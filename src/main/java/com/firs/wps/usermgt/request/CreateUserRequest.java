package com.firs.wps.usermgt.request;

import java.util.Set;

import com.firs.wps.usermgt.annotation.Phone;
import com.firs.wps.usermgt.enums.RoleEnum;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @Size(max = 50, message = "First name cannot be more than 50 characters")
    @Pattern(regexp = "^[\\w \\-\\.\']*$", message = "Name can only contain alphanumeric with space and -.' characters")
    private String firstName;

    @Size(max = 50, message = "Last name cannot be more than 50 characters")
    @Pattern(regexp = "^[\\w \\-\\.\']*$", message = "Name can only contain alphanumeric with space and -.' characters")
    private String lastName;

    @JsonProperty("phone")
    @NotBlank(message = "Phone number is required")
    @Phone(message = "Invalid phone number")
    private String phone;

    @JsonProperty("username")
    @NotBlank(message = "Username address is required")
    private String username;

    @JsonProperty("address")
    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address cannot be more than 500 characters")
    private String address;

    @JsonProperty("roles")
    private Set<RoleEnum> roles;

}
