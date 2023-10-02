package com.firs.wps.usermgt.service;

import com.firs.wps.usermgt.messaging.twilio.TwilioVerification;
import com.firs.wps.usermgt.model.OTP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

@Slf4j
@Service
public class OTPService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RabbitMQService rabbitMQService;

    private TwilioVerification twilioVerification;

    private Locale locale = new Locale("en", "US");
    private ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

    private void sendTokenToPhone(String phone, String code){
        OTP otp = new OTP(phone, "", "sms", code);
        rabbitMQService.sendOTP(otp);
    }

    public  void sendTokenToEmail(String email){
        OTP otp = new OTP("", email, "email", "");
        rabbitMQService.sendOTP(otp);
    }

    public boolean verifyOTP(OTP otp) {
        twilioVerification = new TwilioVerification();
        return  twilioVerification.checkVerification(otp.getPhone(), otp.getCode());
    }

    public void generatePin(String phone) {
        Random passwdIdRandom = new Random();
        String otp = "";
        for (int a = 0; a < 6; a++) {
            int iResult = passwdIdRandom.nextInt(9);
            otp += String.valueOf(iResult);
        }
        sendTokenToPhone(phone, otp);
    }



}
