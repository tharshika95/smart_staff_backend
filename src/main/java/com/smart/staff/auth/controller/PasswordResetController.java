package com.smart.staff.auth.controller;

import com.smart.staff.dto.response.ApiResponse;
import com.smart.staff.auth.dto.request.PasswordUpdateRequest;
import com.smart.staff.auth.service.AuthService;
import com.smart.staff.auth.service.OptService;
import com.smart.staff.dto.response.ErrorDetails;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.smart.staff.auth.constants.AppConstant.API;

@RestController
@RequestMapping(value = API)
public class PasswordResetController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);
    private final OptService optService;
    private final AuthService authService;

    public PasswordResetController(OptService optService, AuthService authService) {
        this.optService = optService;
        this.authService = authService;
    }

    @PostMapping("/sendOtpCode")
    public ResponseEntity<ApiResponse<String>> sendOtpCode(@NotNull @RequestParam String email) {

        // Send email
        optService.sendOTPForPasswordResetToEmail(email);

        return ResponseEntity.ok(ApiResponse.success("Password reset email sent!"));
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<ApiResponse<String>> validateOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = optService.validateOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok(ApiResponse.success("OTP is valid."));
        } else {
            ErrorDetails errorDetails = new ErrorDetails("Invalid OTP", "Invalid or expired OTP.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(errorDetails));
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponse<String>> sendOtpCode(
            @RequestParam Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {

        try {
            authService.resetPassword(userId, currentPassword, newPassword, confirmPassword);
            return ResponseEntity.ok(ApiResponse.success("Password reset successfully."));
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails("Password Reset Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(errorDetails));
        }
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<ApiResponse<String>> updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        try {
            String email = passwordUpdateRequest.getEmail();
            logger.info("Received request to update password for email: {}", email);

            if (!passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getConfirmPassword())) {
                logger.error("Password confirmation does not match for email: {}", email);
                ErrorDetails errorDetails = new ErrorDetails("Password Mismatch", "Passwords do not match.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(errorDetails));
            }

            authService.updatePassword(email, passwordUpdateRequest.getNewPassword());
            logger.info("Successfully updated password for email: {}", email);

            return ResponseEntity.ok(ApiResponse.success("Password updated successfully"));
        } catch (Exception e) {
            logger.error("Error updating password for email {}: {}", passwordUpdateRequest.getEmail(), e.getMessage());
            ErrorDetails errorDetails = new ErrorDetails("Update Password Error", "An error occurred while updating the password.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.failure(errorDetails));
        }
    }
}
