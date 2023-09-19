package com.finlake.models;

import androidx.annotation.NonNull;

import java.sql.Timestamp;

public class FinanceRoomResponse {

    private String id;

    private String name;

    private UserResponse created_by;

    private String room_type;

    private String status;

    private String created_at;

    private String updated_at;

    public FinanceRoomResponse() {

    }

    public FinanceRoomResponse(String id, String name, UserResponse created_by, String room_type, String status, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.created_by = created_by;
        this.room_type = room_type;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserResponse getCreated_by() {
        return created_by;
    }

    public void setCreated_by(UserResponse created_by) {
        this.created_by = created_by;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "FinanceRoomResponse{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", created_by=" + created_by + ", room_type='" + room_type + '\'' + ", status='" + status + '\'' + ", created_at='" + created_at + '\'' + ", updated_at='" + updated_at + '\'' + '}';
    }
}
