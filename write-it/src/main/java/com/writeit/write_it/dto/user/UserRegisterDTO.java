package com.writeit.write_it.dto.user;

public class UserRegisterDTO {
    private String username;
    private String password;

    public UserRegisterDTO() {
    }

    // do i rly need this constructor? consider removing it later, cuz i already
    // have the setters
    public UserRegisterDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
