package com.smart.staff.auth.service;

public interface OptService {
    void sendOTPForPasswordResetToEmail(String toEmail);
    boolean validateOtp(String email, String otp);
}
