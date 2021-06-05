package com.example.nguyenvanquang_b17dcat148.models;

public class PasswordReset {
    private String email;

    public PasswordReset(String email) {
        this.email = email;
    }

    public PasswordReset() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
