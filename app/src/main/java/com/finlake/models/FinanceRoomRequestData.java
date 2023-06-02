package com.finlake.models;

import androidx.annotation.NonNull;

import java.util.List;

public class FinanceRoomRequestData {
    private FinanceRoomBody financeRoomBody;
    private List<UserResponse> userResponseList;

    public FinanceRoomRequestData(FinanceRoomBody financeRoomBody, List<UserResponse> userResponseList) {
        this.financeRoomBody = financeRoomBody;
        this.userResponseList = userResponseList;
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

    @NonNull
    @Override
    public String toString() {
        return "FinanceRoomRequestData{" +
                "financeRoomBody=" + financeRoomBody.toString() +
                ", userResponseList=" + userResponseList.toString() +
                '}';
    }
}
