package com.firs.wps.usermgt.controller;

import com.firs.wps.usermgt.request.PasswordChangeRequest;
import com.firs.wps.usermgt.response.ApiResponse;
import com.firs.wps.usermgt.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordController {

    private final UserService userService;

    @PostMapping("/forgot")
    public ResponseEntity<ApiResponse> forgot(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        return ResponseEntity.ok().body(userService.forgotPassword(passwordChangeRequest));
    }

    @PostMapping("/reset")
    public ResponseEntity<ApiResponse> reset(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        return ResponseEntity.ok().body(userService.resetPassword(passwordChangeRequest));
    }

}
