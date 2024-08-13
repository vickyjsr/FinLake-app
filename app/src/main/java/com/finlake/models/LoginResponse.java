package com.finlake.models;

import com.google.gson.annotations.SerializedName;

import kotlin.ParameterName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

public class LoginResponse {

    @SerializedName("requestId")
    private String requestId;

    @SerializedName("responseCode")
    private String responseCode;

    @SerializedName("responseMessage")
    private String errorMessage;

    @SerializedName("data")
    private Data data;

    public LoginResponse() {
    }

    public LoginResponse(String requestId, String responseCode, String errorMessage, Data data) {
        this.requestId = requestId;
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public class Data {
        @SerializedName("token")
        private String token;

        @SerializedName("userId")
        private String userId;

        @SerializedName("requestId")
        private String requestId;

        public Data() {
        }

        public Data(String token, String userId, String requestId) {
            this.token = token;
            this.userId = userId;
            this.requestId = requestId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
