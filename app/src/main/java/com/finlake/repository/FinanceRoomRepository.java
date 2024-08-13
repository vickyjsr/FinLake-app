package com.finlake.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.finlake.interfaces.FinanceRoomInterface;
import com.finlake.models.FinanceRoomRequestData;
import com.finlake.models.FinanceRoomResponse;
import com.finlake.models.Pageable;
import com.finlake.models.response.FinanceRoomListResponse;
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

    public void getAllFinanceRoomByUserId(String requestId, int page, int pageSize, String status, String authToken, String id, FinanceRoomInterface financeRoomInterface) {
        Call<FinanceRoomListResponse> financeRoomResponseCall = financeRoomService.getAllFinanceRoomByUserId(authToken, requestId, page, pageSize, status, id);
        financeRoomResponseCall.enqueue(new Callback<FinanceRoomListResponse>() {
            @Override
            public void onResponse(@NonNull Call<FinanceRoomListResponse> call, @NonNull Response<FinanceRoomListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FinanceRoomListResponse financeRoomListResponse = response.body();
                    List<FinanceRoomResponse> financeRoomResponses = financeRoomListResponse.getData().getContent();
                    for (FinanceRoomResponse financeRoomResponse : financeRoomResponses) {
                        System.out.println(financeRoomResponse);
                    }
                    financeRoomInterface.onResponseList(financeRoomResponses);
                } else if (response.code() == 401) { // Unauthenticated
                    financeRoomInterface.redirectToLogin();
                } else {
                    financeRoomInterface.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FinanceRoomListResponse> call, @NonNull Throwable t) {
                financeRoomInterface.onFailure(t);
            }
        });
    }
}
