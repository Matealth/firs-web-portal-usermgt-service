package com.firs.wps.usermgt.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firs.wps.usermgt.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateRequest {

    @JsonProperty("username")
    @NotBlank(message = "username address is required")
    private String username;

    @JsonProperty("roles")
    private Set<RoleEnum> roles;

}
