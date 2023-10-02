package com.firs.wps.usermgt.controller;

import com.firs.wps.usermgt.request.CreateUserRequest;
import com.firs.wps.usermgt.request.RoleUpdateRequest;
import com.firs.wps.usermgt.response.ApiResponse;
import com.firs.wps.usermgt.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> finUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.findUser(username));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findUsers() {
        return ResponseEntity.ok().body(userService.findUsers());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok().body(userService.createUser(createUserRequest));
    }

    @PostMapping("/modify-role")
    public ResponseEntity<ApiResponse> modifyRole(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        return ResponseEntity.ok().body(userService.modifyRole(roleUpdateRequest));
    }


}
