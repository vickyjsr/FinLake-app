package com.finlake.models;

import androidx.annotation.NonNull;

public class FinanceRoomBody {
    private String name;

    private String created_by;

    private String room_type;

    public FinanceRoomBody() {

    }

    public FinanceRoomBody(String name, String created_by, String room_type) {
        this.name = name;
        this.created_by = created_by;
        this.room_type = room_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    @NonNull
    @Override
    public String toString() {
        return "FinanceRoomBody{" +
                "name='" + name + '\'' +
                ", created_by='" + created_by + '\'' +
                ", room_type='" + room_type + '\'' +
                '}';
    }
}
