package com.writeit.write_it.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.writeit.write_it.common.exception.AppException;
import com.writeit.write_it.common.exception.ExceptionMessage;
import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.dto.request.ForgotPasswordRequestDTO;
import com.writeit.write_it.dto.request.LoginRequestDTO;
import com.writeit.write_it.dto.request.RegisterRequestDTO;
import com.writeit.write_it.dto.request.ResetPasswordRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;
import com.writeit.write_it.entity.Token;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.entity.enums.TokensPurpose;
import com.writeit.write_it.security.jwt.JwtUtils;
import com.writeit.write_it.security.userdetails.CustomUserDetails;
import com.writeit.write_it.service.email.EmailService;
import com.writeit.write_it.service.token.TokenService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
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
            throw new AppException(ExceptionMessage.USERNAME_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), encodedPassword, request.getDisplayedName());
        userDAO.create(user);

        RegisterResponseDTO responseDTO = new RegisterResponseDTO(user.getDisplayedName(), user.getStatus());

        return responseDTO;
    }

    @Override
    public AuthTokenResponseDTO login(LoginRequestDTO request) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
                    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

            if (!userDetails.isEnabled()) {
                throw new AppException(ExceptionMessage.USER_DEACTIVATED);
            }
            
            User user = userDetails.getUser();

            String accessToken = jwtUtils.generateToken(user);
            Token refreshToken = tokenService.createRefreshToken(user, request.getDeviceInfo());

            AuthTokenResponseDTO responseDTO = new AuthTokenResponseDTO(accessToken, refreshToken.getToken());
            return responseDTO;
        } catch (Exception ex) {
            switch (ex.getMessage()){
                case ExceptionMessage.INVALID_CREDENTIALS -> throw new AppException(ExceptionMessage.INVALID_CREDENTIALS);
                case ExceptionMessage.USER_DEACTIVATED -> throw new AppException(ExceptionMessage.USER_DEACTIVATED);
            }
        }
        return null;
    }

    @Override
    public AuthTokenResponseDTO refresh(String token) {
        Token refreshToken;
        try {
            refreshToken = tokenService.validateRefreshToken(token);
        } catch (Exception ex) {
            throw new AppException(ExceptionMessage.INVALID_REFRESH_TOKEN);
        }

        tokenService.revokeRefreshToken(token);

        User user = refreshToken.getUser();
        String accessToken = jwtUtils.generateToken(user);
        Token newRefreshToken = tokenService.createRefreshToken(user, refreshToken.getDeviceInfo());

        AuthTokenResponseDTO responseDTO = new AuthTokenResponseDTO(accessToken, newRefreshToken.getToken());
        return responseDTO;
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDTO request) {
        User user = userDAO.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ExceptionMessage.NO_USER_WITH_GIVEN_EMAIL));
        Token passwordResetToken = tokenService.createSingleUseToken(user, TokensPurpose.RESET_PASSWORD);
        sendPasswordResetEmail(user.getEmail(), passwordResetToken.getToken());
    }

    private void sendPasswordResetEmail(String recipient, String token) {
        String subject = "WriteIt Password Reset Request";
        String url = "http://writeit/reset-password?token=" + token;
        String body = "Click the link to reset your password: " + url;
        emailService.sendEmail(recipient, subject, body);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDTO request) {
        Token token = tokenService.validateSingleUseToken(request.getToken());
        User user = token.getUser();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        userDAO.update(user);
    }
}
