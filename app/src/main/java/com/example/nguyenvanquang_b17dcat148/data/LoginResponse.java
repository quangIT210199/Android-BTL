package com.example.nguyenvanquang_b17dcat148.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("tokenType")
    private String tokenType = "Bearer";

    @SerializedName("id")
    private Integer id;

    @SerializedName("username")
    private String username;

    @SerializedName("roles")
    private List<String> roles;

    public LoginResponse(String accessToken, Integer id, String username, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
