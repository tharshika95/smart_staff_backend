package com.smart.staff.auth.service.impl;


import com.smart.staff.auth.service.OptService;
import com.smart.staff.auth.util.CodeGenerator;
import com.smart.staff.auth.entity.User;
import com.smart.staff.auth.exception.UserNotFoundException;
import com.smart.staff.auth.repo.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OptServiceImpl implements OptService {


    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private static final int OTP_VALID_DURATION = 2; // 2 minutes

    public OptServiceImpl(JavaMailSender mailSender, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @Override
    public void sendOTPForPasswordResetToEmail(String toEmail) {

        // Get the current authenticated user's email
        //String authenticatedUEmail = getAuthenticatedEmail();

        // Fetch the user by name
        Optional<User> user = userRepository.findByEmail(toEmail);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Not a valid user");
        }

        // Ensure the authenticated email matches the requested email
        if (!user.get().getEmail().equals(toEmail)) {
            throw new SecurityException("You are not authorized to reset the password for this email.");
        }

        // Generate token
        String otp = CodeGenerator.generate6DigitCode();

        // Set OTP expiration time
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(OTP_VALID_DURATION);

        // Save OTP to the database
        User otpUser = user.get();
        otpUser.setCode(otp);
        otpUser.setExpirationTime(expirationTime);
        userRepository.save(otpUser);

        // Create and send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + ". It is valid for 2 minutes.");
        message.setFrom("karnan.lerning@example.com");

        mailSender.send(message);
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        Optional<User> otpEntityOptional = userRepository.findByEmail(email);

        if (otpEntityOptional.isPresent()) {
            User otpEntity = otpEntityOptional.get();

            // Check if OTP is correct and within the valid duration
            return otpEntity.getCode().equals(otp) && LocalDateTime.now().isBefore(otpEntity.getExpirationTime());
        }

        return false;
    }

    private static String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                authenticatedEmail = ((UserDetails) principal).getUsername();
            } else {
                authenticatedEmail = principal.toString();
            }
        }
        return authenticatedEmail;
    }
}
