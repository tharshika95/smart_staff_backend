package com.smart.staff.auth.service.impl;

import com.smart.staff.auth.dto.request.SignupRequest;
import com.smart.staff.auth.exception.SignUpException;
import com.smart.staff.auth.service.AuthService;
import com.smart.staff.auth.entity.ERole;
import com.smart.staff.auth.entity.Role;
import com.smart.staff.auth.entity.User;
import com.smart.staff.auth.exception.UserNotFoundException;
import com.smart.staff.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User signUp(SignupRequest signupRequest) {

        try{
            User user = User.builder()
                    .username(signupRequest.getUsername())
                    .email(signupRequest.getEmail())
                    .phoneNumber(signupRequest.getPhoneNumber())
                    .password( new BCryptPasswordEncoder().encode(signupRequest.getPassword()))
                    .roles(new HashSet<>(Collections.singletonList(Role.builder().name(ERole.ROLE_USER).build())))
                    .build();
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception occurred on signUp method",e);
            throw new SignUpException("Signup error");
        }
    }

    @Override
    public void resetPassword(Long userId, String currentPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        // Assuming you have a method to encode passwords
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        userRepository.save(user);
        logger.info("Password updated for user with email: {}", email);
    }
}
