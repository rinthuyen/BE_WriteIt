package com.writeit.write_it.service.auth;

import java.util.Optional;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.writeit.write_it.common.exception.ApiError;
import com.writeit.write_it.common.exception.AppException;
import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.dto.request.ForgotPasswordRequestDTO;
import com.writeit.write_it.dto.request.LoginRequestDTO;
import com.writeit.write_it.dto.request.RegisterRequestDTO;
import com.writeit.write_it.dto.request.ResetPasswordRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;
import com.writeit.write_it.entity.Token;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.entity.enums.Status;
import com.writeit.write_it.entity.enums.TokensPurpose;
import com.writeit.write_it.security.jwt.JwtUtils;
import com.writeit.write_it.security.userdetails.CustomUserDetails;
import com.writeit.write_it.service.email.EmailService;
import com.writeit.write_it.service.token.TokenService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TokenService tokenService;
    private final EmailService emailService;

    @Override
    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        if (userDAO.existsByUsername(request.getUsername())) {
            throw new AppException(ApiError.USERNAME_ALREADY_EXISTS);
        }

        String encoded = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), encoded, request.getDisplayedName());
        userDAO.create(user);

        return new RegisterResponseDTO(user.getDisplayedName(), user.getStatus());
    }

    @Override
    @Transactional
    public AuthTokenResponseDTO login(LoginRequestDTO request) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
                )
            );
        } catch (AuthenticationException ex) {
            if (ex instanceof DisabledException)           throw new AppException(ApiError.USER_DEACTIVATED);
            if (ex instanceof LockedException)             throw new AppException(ApiError.ACCOUNT_LOCKED);
            if (ex instanceof AccountExpiredException)     throw new AppException(ApiError.ACCOUNT_EXPIRED);
            if (ex instanceof CredentialsExpiredException) throw new AppException(ApiError.CREDENTIALS_EXPIRED);
            throw new AppException(ApiError.CREDENTIALS_INVALID);
        }

        CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();
        User user = principal.getUser();

        String accessToken = jwtUtils.generateToken(user);
        Token refreshToken = tokenService.createRefreshToken(user, request.getDeviceInfo());

        return new AuthTokenResponseDTO(accessToken, refreshToken.getToken());
    }


    @Override
    @Transactional
    public AuthTokenResponseDTO refresh(String token) {
        Token refreshToken = tokenService.validateRefreshToken(token);

        User user = refreshToken.getUser();
        if (user.getStatus() == Status.DEACTIVATED) {
            tokenService.revokeRefreshToken(token);
            throw new AppException(ApiError.USER_DEACTIVATED);
        }

        tokenService.revokeRefreshToken(token);
        String access = jwtUtils.generateToken(user);
        Token newRt = tokenService.createRefreshToken(user, refreshToken.getDeviceInfo());

        return new AuthTokenResponseDTO(access, newRt.getToken());
    }


    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequestDTO request) {
        Optional<User> userOpt = userDAO.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            // return 200 regardless 
            return;
        }
        User user = userOpt.get();

        Token passwordResetToken = tokenService.createSingleUseToken(user, TokensPurpose.RESET_PASSWORD);

        String token = passwordResetToken.getToken();

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override public void afterCommit() {
                try {
                    sendPasswordResetEmail(user.getEmail(), token);
                } catch (RuntimeException e) {
                    log.warn("Failed to send reset email to {}", user.getEmail(), e);
                }
            }
        });
    }

    private void sendPasswordResetEmail(String recipient, String token) {
        String subject = "WriteIt Password Reset Request";
        String url = "http://writeit/reset-password?token=" + token;
        String body = "Click the link to reset your password: " + url;
        emailService.sendEmail(recipient, subject, body);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequestDTO request) {
        Token token = tokenService.validateSingleUseToken(request.getToken());
        User user = token.getUser();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        userDAO.update(user);
    }
}
