package com.writeit.write_it.dto.user;

public class UserRegisterResponseDTO {
    private String displayedName;
    private String status;

    public UserRegisterResponseDTO() {
    }

    public UserRegisterResponseDTO(String displayedName, String status) {
        this.displayedName = displayedName;
        this.status = status;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
