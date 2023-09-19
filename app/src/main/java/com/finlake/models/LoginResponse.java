package com.finlake.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    String token;

    @SerializedName("errorMessage")
    String errorMessage;

    @SerializedName("user_id")
    String user_id;

    public LoginResponse() {
    }

    public LoginResponse(String token, String errorMessage, String user_id) {
        this.token = token;
        this.user_id = user_id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
