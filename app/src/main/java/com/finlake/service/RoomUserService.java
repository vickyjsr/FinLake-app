package com.finlake.service;

import com.finlake.models.FinanceRoomRequestData;
import com.finlake.models.FinanceRoomResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RoomUserService {
    @POST("newFinanceRoom")
    Call<FinanceRoomResponse> createRoomUser(@Header("Authorization") String authToken, @Body FinanceRoomRequestData financeRoomRequestData);

    @GET("filterUserFromFinanceRoom")
    Call<List<FinanceRoomResponse>> getAllRoomUserByUserId(@Header("Authorization") String authToken, @Query("id") String id);
}
