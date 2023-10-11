package com.finlake.interfaces;

import com.finlake.models.FinanceRoomResponse;
import com.finlake.models.RoomUserResponse;

import java.util.List;

public interface FinanceRoomInterface {
    void onResponse(FinanceRoomResponse financeRoomResponse);

    void onResponseList(List<FinanceRoomResponse> roomUserResponses);

    void onFailure(Throwable throwable);

    void redirectToLogin();
}
