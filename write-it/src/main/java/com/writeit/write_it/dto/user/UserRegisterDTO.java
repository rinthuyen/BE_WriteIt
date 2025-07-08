package com.writeit.write_it.dto.user;

public class UserRegisterDTO {
    private String username;
    private String password;
    private String displayedName;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String username, String password, String displayedName) {
        this.username = username;
        this.password = password;
        this.displayedName = displayedName;
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

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getDisplayedName() {
        return displayedName;
    }
}
