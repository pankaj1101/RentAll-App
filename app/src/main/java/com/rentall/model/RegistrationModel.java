package com.rentall.model;

//
public class RegistrationModel {
    private String email;
    private String mobile;
    private String displayName;

    public RegistrationModel() {
    }

    public RegistrationModel(String email, String displayName, String mobile) {
        this.email = email;
        this.displayName = displayName;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDisplayName() {
        return displayName;
    }
}
