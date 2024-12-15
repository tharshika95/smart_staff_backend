package com.smart.staff.auth.service;

import com.smart.staff.auth.entity.RefreshToken;
import com.smart.staff.auth.entity.User;
import com.smart.staff.auth.exception.TokenRefreshException;
import com.smart.staff.auth.repo.RefreshTokenRepository;
import com.smart.staff.auth.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenService {
    @Value("${smartStaff.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userName) {
        // Fetch the user by email
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userName));

        // Check if a refresh token already exists for the user
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(user.getId());

        RefreshToken refreshToken;
        if (existingToken.isPresent()) {
            // Update the existing token
            refreshToken = existingToken.get();
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
            log.info("Updating existing refresh token for user: {}", userName);
        } else {
            // Create a new refresh token
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
            log.info("Creating new refresh token for user: {}", userName);
        }

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
