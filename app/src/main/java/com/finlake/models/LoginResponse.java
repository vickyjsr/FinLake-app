package com.finlake.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    String token;

    @SerializedName("errorMessage")
    String errorMessage;

    public LoginResponse() {
    }

    public LoginResponse(String token, String errorMessage) {
        this.token = token;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
