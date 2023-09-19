package com.finlake.service;

import com.finlake.models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserService {
    @GET("listAllUsers")
    Call<List<UserResponse>> getAllUsers(@Header("Authorization") String authToken);

    @GET("listAllUsersFiltered")
    Call<List<UserResponse>> getAllUsersFiltered(@Header("Authorization") String authToken, @Query(value = "id") String id);
}
