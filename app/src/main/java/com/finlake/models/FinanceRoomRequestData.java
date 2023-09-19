package com.finlake.models;

import androidx.annotation.NonNull;

import java.util.List;

public class FinanceRoomRequestData {
    private FinanceRoomBody financeRoomBody;
    private List<UserResponse> userResponseList;

    private String userId;

    public FinanceRoomRequestData(FinanceRoomBody financeRoomBody, List<UserResponse> userResponseList, String userId) {
        this.financeRoomBody = financeRoomBody;
        this.userResponseList = userResponseList;
        this.userId = userId;
    }

    public FinanceRoomBody getFinanceRoomBody() {
        return financeRoomBody;
    }

    public void setFinanceRoomBody(FinanceRoomBody financeRoomBody) {
        this.financeRoomBody = financeRoomBody;
    }

    public List<UserResponse> getUserResponseList() {
        return userResponseList;
    }

    public void setUserResponseList(List<UserResponse> userResponseList) {
        this.userResponseList = userResponseList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FinanceRoomRequestData{" +
                "financeRoomBody=" + financeRoomBody +
                ", userResponseList=" + userResponseList +
                ", userId='" + userId + '\'' +
                '}';
    }
}
