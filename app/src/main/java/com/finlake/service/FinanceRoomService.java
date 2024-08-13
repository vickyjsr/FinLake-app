package com.finlake.service;

import com.finlake.models.FinanceRoomRequestData;
import com.finlake.models.FinanceRoomResponse;
import com.finlake.models.RoomUserResponse;
import com.finlake.models.response.FinanceRoomListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FinanceRoomService {
    @POST("room/new")
    Call<FinanceRoomResponse> createFinanceRoom(@Header("authorization") String authToken, @Body FinanceRoomRequestData financeRoomRequestData);

    @GET("room/filter")
    Call<FinanceRoomListResponse> getAllFinanceRoomByUserId(@Header("authorization") String authToken,
                                                            @Header(value = "requestId") String requestId,
                                                            @Query(value = "page") int page,
                                                            @Query(value = "size") int pageSize, @Query(value = "status") String status,
                                                            @Query("userId") String id);
}
