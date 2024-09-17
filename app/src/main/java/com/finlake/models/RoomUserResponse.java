package com.finlake.models;

import java.sql.Timestamp;

public class RoomUserResponse {
    private String id;

    private String balance;

    private FinanceRoomResponse finance_room;

    private UserResponse user;

    private String created_at;

    private String updated_at;

    public RoomUserResponse() {

    }

    public RoomUserResponse(String id, String balance, FinanceRoomResponse finance_room, UserResponse user, String created_at, String updated_at) {
        this.id = id;
        this.balance = balance;
        this.finance_room = finance_room;
        this.user = user;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public FinanceRoomResponse getFinance_room() {
        return finance_room;
    }

    public void setFinance_room(FinanceRoomResponse finance_room) {
        this.finance_room = finance_room;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "RoomUserResponse{" +
                "id='" + id + '\'' +
                ", balance='" + balance + '\'' +
                ", finance_room=" + finance_room +
                ", user=" + user +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
