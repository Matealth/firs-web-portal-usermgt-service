package com.firs.wps.usermgt.messaging.twilio;

public interface VerificationService {
    void startVerification(String recipient, String channel);
    boolean checkVerification(String recipient, String code);
}