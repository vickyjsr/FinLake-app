package com.finlake.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.finlake.interfaces.FinanceRoomInterface;
import com.finlake.interfaces.UserResponseInterface;
import com.finlake.models.FinanceRoomBody;
import com.finlake.models.FinanceRoomRequestData;
import com.finlake.models.FinanceRoomResponse;
import com.finlake.models.RoomUserResponse;
import com.finlake.models.UserResponse;
import com.finlake.retorfit.RetrofitClientInstance;
import com.finlake.service.FinanceRoomService;
import com.finlake.service.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomRepository {

    FinanceRoomService financeRoomService;

    public RoomRepository() {
        financeRoomService = RetrofitClientInstance.getInstance().create(FinanceRoomService.class);
    }

    public void createFinanceRoom(String authToken, FinanceRoomRequestData financeRoomRequestData, FinanceRoomInterface financeRoomInterface) {
        Call<FinanceRoomResponse> financeRoomResponseCall = financeRoomService.createFinanceRoom(authToken, financeRoomRequestData);

        financeRoomResponseCall.enqueue(new Callback<FinanceRoomResponse>() {
            @Override
            public void onResponse(@NonNull Call<FinanceRoomResponse> call, @NonNull Response<FinanceRoomResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    financeRoomInterface.onResponse(response.body());
                } else if (response.code() == 401) { // Unauthenticated
                    financeRoomInterface.redirectToLogin();
                } else {
                    financeRoomInterface.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FinanceRoomResponse> call, @NonNull Throwable t) {
                financeRoomInterface.onFailure(t);
            }
        });
    }

    public void getAllFinanceRoomByUserId(String authToken, String userId, FinanceRoomInterface financeRoomInterface) {
        Call<List<FinanceRoomResponse>> financeRoomResponseCall = financeRoomService.getAllFinanceRoomByUserId(authToken, userId);
        financeRoomResponseCall.enqueue(new Callback<List<FinanceRoomResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<FinanceRoomResponse>> call, @NonNull Response<List<FinanceRoomResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println(response.body());
                    financeRoomInterface.onResponseList(response.body());
                } else if (response.code() == 401) { // Unauthenticated
                    financeRoomInterface.redirectToLogin();
                } else {
                    financeRoomInterface.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FinanceRoomResponse>> call, @NonNull Throwable t) {
                financeRoomInterface.onFailure(t);
            }
        });
    }
}
