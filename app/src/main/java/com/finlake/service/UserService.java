package com.finlake.service;

import com.finlake.models.UserResponse;
import com.finlake.models.response.UserListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserService {
    @GET("user/list")
    Call<List<UserResponse>> getAllUsers(@Header("authorization") String authToken,
                                         @Header(value = "requestId") String requestId,
                                         @Query(value = "page") int page,
                                         @Query(value = "size") int pageSize);

    @GET("user/list/except")
    Call<UserListResponse> getAllUsersFiltered(@Header("authorization") String authToken,
                                               @Header(value = "requestId") String requestId,
                                               @Query(value = "userIds") List<String> userIds,
                                               @Query(value = "page") int page,
                                               @Query(value = "size") int pageSize);
}
