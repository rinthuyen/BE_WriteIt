package com.writeit.write_it.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.writeit.write_it.common.exception.UsernameAlreadyExistsException;
import com.writeit.write_it.common.mapper.UserMapper;
import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.entity.RefreshToken;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.payload.request.UserLoginRequestDTO;
import com.writeit.write_it.payload.request.UserRegisterRequestDTO;
import com.writeit.write_it.payload.response.UserLoginResponseDTO;
import com.writeit.write_it.payload.response.UserRegisterResponseDTO;
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

    public AuthServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    @Transactional
    public UserRegisterResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO) {
        if (userDAO.existsByUsername(userRegisterRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(userRegisterRequestDTO.getPassword());
        userRegisterRequestDTO.setPassword(encodedPassword);

        User user = UserMapper.UserRegisterDTOtoUser(userRegisterRequestDTO);
        userDAO.save(user);
        UserRegisterResponseDTO userRegisterResponseDTO = UserMapper.UserToUserRegisterResponseDTO(user);
        return userRegisterResponseDTO;
    }

    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getUsername(),
                        userLoginRequestDTO.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        String accessToken = jwtUtils.generateToken(user.getUsername());
        RefreshToken refreshToken = refreshTokenService.create(user, userLoginRequestDTO.getDeviceInfo());
        return new UserLoginResponseDTO(accessToken, refreshToken.getToken());
    }

    @Override
    public UserLoginResponseDTO refresh(String token) {
        RefreshToken refreshToken = refreshTokenService.validate(token);
        refreshTokenService.revoke(token);
        User user = refreshToken.getUser();
        String accessToken = jwtUtils.generateToken(user.getUsername());
        RefreshToken newRefreshToken = refreshTokenService.create(user, refreshToken.getDeviceInfo());
        return new UserLoginResponseDTO(accessToken, newRefreshToken.getToken());
    }

}
