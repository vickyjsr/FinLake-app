package com.finlake.interfaces;

import com.finlake.models.FinanceRoomResponse;
import com.finlake.models.UserResponse;

import java.util.List;

public interface OnClickSelectionListener {
    void selectedItem(List<UserResponse> selectedUsers);
}
