package com.firs.wps.usermgt.model;

import java.util.Date;
import java.util.Set;
import lombok.Data;

import com.firs.wps.usermgt.entity.Role;

@Data
public class UserModel {

    private String phone;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String branchId;
    private String password;
    private Date lastLogin;
    private String emailOtp;
    private Boolean enabled;
    private Boolean active;
    private String address;
    private Set<Role> roles;
}
