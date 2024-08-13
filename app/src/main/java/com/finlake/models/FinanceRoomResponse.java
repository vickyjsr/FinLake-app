package com.finlake.models;

public class FinanceRoomResponse {

    private String id;

    private String name;

    private String createdBy;

    private String roomType;

    private String status;

    private String createdAt;

    private String updatedAt;

    public FinanceRoomResponse() {

    }

    public FinanceRoomResponse(String id, String name, String createdBy, String roomType, String status, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.roomType = roomType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "FinanceRoomResponse{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", createdBy=" + createdBy + ", roomType='" + roomType + '\'' + ", status='" + status + '\'' + ", createdAt='" + createdAt + '\'' + ", updatedAt='" + updatedAt + '\'' + '}';
    }
}
