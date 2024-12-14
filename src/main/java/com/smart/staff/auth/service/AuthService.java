package com.smart.staff.auth.service;


import com.smart.staff.auth.dto.request.SignupRequest;
import com.smart.staff.auth.entity.User;

public interface AuthService {

    User signUp(SignupRequest signupRequest);

    void resetPassword(Long userId, String currentPassword, String newPassword, String confirmPassword);

    void updatePassword(String email, String newPassword);
}
