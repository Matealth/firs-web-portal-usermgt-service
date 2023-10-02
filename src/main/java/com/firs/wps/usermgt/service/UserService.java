package com.firs.wps.usermgt.service;

import com.firs.wps.usermgt.entity.Role;
import com.firs.wps.usermgt.entity.User;
import com.firs.wps.usermgt.enums.RoleEnum;
import com.firs.wps.usermgt.model.OTP;
import com.firs.wps.usermgt.model.UserModel;
import com.firs.wps.usermgt.repo.RoleRepo;
import com.firs.wps.usermgt.repo.UserRepo;
import com.firs.wps.usermgt.request.CreateUserRequest;
import com.firs.wps.usermgt.request.PasswordChangeRequest;
import com.firs.wps.usermgt.request.RoleUpdateRequest;
import com.firs.wps.usermgt.response.ApiResponse;
import com.firs.wps.usermgt.util.ApiUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final OTPService otpService;
    private final TokenizerService tokenizerService;
    private Locale locale = new Locale("en", "NG");
    private ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

    public ApiResponse findUser(String username)
    {
        ApiResponse apiResponse = null;
        try {
            User user = userRepo.findByUsername(username).orElseThrow();
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(user, userModel);
            apiResponse = ApiUtil.prepareResponse(userModel);
        } catch (Exception e) {
            apiResponse = ApiUtil.prepareError(e);
        }
        return apiResponse;
    }

    public ApiResponse findUsers() { // to do pagination
        ApiResponse apiResponse = null;
        try {
            List<User> users = userRepo.findAllUsers();
            List<UserModel> userModelList = new ArrayList<>();
            BeanUtils.copyProperties(users, userModelList);
            apiResponse = ApiUtil.prepareResponse(userModelList);
        } catch (Exception e) {
            apiResponse = ApiUtil.prepareError(e);
        }
        return apiResponse;
    }

    public ApiResponse createUser(CreateUserRequest createUserRequest)
    {
        ApiResponse apiResponse = null;
        Optional<User> userCheck = userRepo.findByUsername(createUserRequest.getUsername());

        if (userCheck.isPresent()) {
            return ApiUtil.prepareResponse("user with username : "  + createUserRequest.getUsername() + "  already exists", false);
        }

        try {
           User user = mapRegisterRequestToUser(createUserRequest);
            user.setEnabled(false);
            user = userRepo.save(user);
            addRoleToUser(user, createUserRequest.getRoles());
            user.setActive(false);
            apiResponse = ApiUtil.prepareResponse(createUserRequest);
        } catch (Exception e) {
            apiResponse = ApiUtil.prepareError(e);
        }
        return apiResponse;
    }

    public void addRoleToUser(User user, Set<RoleEnum> roles) {
               roles.stream()
                .forEach(roleEnum -> {
                    Role role = roleRepo.findRoleByName(roleEnum.getValue());
                    user.getRoles().add(role);
                });
    }

    public ApiResponse modifyRole(RoleUpdateRequest roleUpdateRequest){
        ApiResponse apiResponse = null;
        try {
        User user = userRepo.findByUsername(roleUpdateRequest.getUsername()).orElseThrow();
        addRoleToUser(user, roleUpdateRequest.getRoles());
            apiResponse = ApiUtil.prepareResponse(roleUpdateRequest);
    } catch(Exception e) {
            apiResponse = ApiUtil.prepareError(e);
        }
        return apiResponse;
    }

    public ApiResponse forgotPassword(PasswordChangeRequest passwordResetRequest){
        ApiResponse apiResponse = null;
        try {
        User user = userRepo.findByUsername(passwordResetRequest.getUsername()).orElseThrow();
        if (passwordResetRequest.getOtp() == null) {
          otpService.generatePin(user.getPhone());
          apiResponse = ApiUtil.prepareResponse(messages.getString("enter-token") + user.getPhone().substring(0, 6)
                  + "****" + user.getPhone().substring(10), true);
        } else {

         OTP otp = new OTP(user.getPhone(), "", "sms", passwordResetRequest.getOtp());
         boolean verificationResult = otpService.verifyOTP(otp);

         if (!verificationResult) {
             apiResponse = ApiUtil.prepareResponse(messages.getString("wrong-otp") , false);
         }else{
             user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
             apiResponse = ApiUtil.prepareResponse(messages.getString("password-change-success") , true);
           }
         }
        }catch (Exception ex){
            apiResponse = ApiUtil.prepareError(ex);
        }
        return apiResponse;
    }

    public ApiResponse resetPassword(PasswordChangeRequest passwordChangeRequest){
        ApiResponse apiResponse = null;
                try {
                    User user = userRepo.findByUsername(passwordChangeRequest.getUsername()).orElseThrow();
                    user.setPassword(passwordEncoder.encode(passwordChangeRequest.getPassword()));
                    apiResponse = ApiUtil.prepareResponse(messages.getString("password-change-success") , true);
                } catch (Exception ex) {
                    apiResponse = ApiUtil.prepareError(ex);
                }
        return apiResponse;
    }

    public User mapRegisterRequestToUser(CreateUserRequest createUserRequest){
        User user = User.builder()
                .firstname(createUserRequest.getFirstName())
                .lastname(createUserRequest.getLastName())
                .phone(createUserRequest.getPhone())
                .email(createUserRequest.getUsername())
                .username(createUserRequest.getUsername())
                .enabled(true)
                .address(createUserRequest.getAddress())
                // default password. Must be changed by user on first login.
                .password(passwordEncoder.encode("Quality@123"))
                .roles(new HashSet<>())
                .active(true)
                .build();
        return user;
    }

}
