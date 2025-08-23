package com.writeit.write_it.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.writeit.write_it.common.exception.CustomException;
import com.writeit.write_it.common.exception.ExceptionMessage;
import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.dto.request.LoginRequestDTO;
import com.writeit.write_it.dto.request.RegisterRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;
import com.writeit.write_it.entity.RefreshToken;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.security.jwt.JwtUtils;
import com.writeit.write_it.security.userdetails.CustomUserDetails;
import com.writeit.write_it.service.refresh_token.RefreshTokenService;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(
            UserDAO userDAO,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            RefreshTokenService refreshTokenService) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {
        if (userDAO.existsByUsername(requestDTO.getUsername())) {
            throw new CustomException(ExceptionMessage.UsernameAlreadyExists);
        }

        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        User user = new User(requestDTO.getUsername(), encodedPassword, requestDTO.getDisplayedName());
        userDAO.create(user);

        RegisterResponseDTO responseDTO = new RegisterResponseDTO(user.getDisplayedName(), user.getStatus());

        return responseDTO;
    }

    @Override
    public AuthTokenResponseDTO login(LoginRequestDTO requestDTO) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getUsername(),
                            requestDTO.getPassword()));
                    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

            if (!userDetails.isEnabled()) {
                throw new CustomException(ExceptionMessage.UserDeactivated);
            }
            
            User user = userDetails.getUser();

            String accessToken = jwtUtils.generateToken(user);
            RefreshToken refreshToken = refreshTokenService.create(user, requestDTO.getDeviceInfo());

            AuthTokenResponseDTO responseDTO = new AuthTokenResponseDTO(accessToken, refreshToken.getToken());
            return responseDTO;
        } catch (Exception ex) {
            switch (ex.getMessage()){
                case ExceptionMessage.InvalidCredentials:
                    throw new CustomException(ExceptionMessage.InvalidCredentials);
                case ExceptionMessage.UserDeactivated:
                    throw new CustomException(ExceptionMessage.UserDeactivated);
            }
        }
        return null;
    }

    @Override
    public AuthTokenResponseDTO refresh(String token) {
        RefreshToken refreshToken;
        try {
            refreshToken = refreshTokenService.validate(token);
        } catch (Exception ex) {
            throw new CustomException(ExceptionMessage.InvalidRefreshToken);
        }

        refreshTokenService.revoke(token);

        User user = refreshToken.getUser();
        String accessToken = jwtUtils.generateToken(user);
        RefreshToken newRefreshToken = refreshTokenService.create(user, refreshToken.getDeviceInfo());

        AuthTokenResponseDTO responseDTO = new AuthTokenResponseDTO(accessToken, newRefreshToken.getToken());
        return responseDTO;
    }

}
