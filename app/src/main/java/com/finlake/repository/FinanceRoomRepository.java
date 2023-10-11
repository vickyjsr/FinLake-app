package com.finlake.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.finlake.interfaces.FinanceRoomInterface;
import com.finlake.models.FinanceRoomRequestData;
import com.finlake.models.FinanceRoomResponse;
import com.finlake.retorfit.RetrofitClientInstance;
import com.finlake.service.FinanceRoomService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class FinanceRoomRepository {

    FinanceRoomService financeRoomService;

    public FinanceRoomRepository() {
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

    public void getAllFinanceRoomByUserId(int page, int pageSize, boolean pagination, String status, String authToken, String id, FinanceRoomInterface financeRoomInterface) {
        Call<List<FinanceRoomResponse>> financeRoomResponseCall = financeRoomService.getAllFinanceRoomByUserId(authToken, page, pageSize, pagination, status, id);
        financeRoomResponseCall.enqueue(new Callback<List<FinanceRoomResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<FinanceRoomResponse>> call, @NonNull Response<List<FinanceRoomResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for(FinanceRoomResponse financeRoomResponse:response.body()){
                        System.out.println(financeRoomResponse);
                    }
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
