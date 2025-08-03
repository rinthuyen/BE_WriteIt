package com.writeit.write_it.dto.user;

public class UserRegisterResponseDTO {
    private String username;
    private String status;

    public UserRegisterResponseDTO() {
        // Default constructor
    }

    public UserRegisterResponseDTO(String username, String password, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
