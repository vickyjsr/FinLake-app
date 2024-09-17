package com.finlake.models;

import com.google.gson.annotations.SerializedName;

public class LoginBody {

    @SerializedName("requestId")
    private String requestId;

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public LoginBody() {
    }

    public LoginBody(String requestId, String email, String password) {
        this.requestId = requestId;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
