package com.finlake.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.finlake.interfaces.UserResponseInterface;
import com.finlake.models.UserResponse;
import com.finlake.retorfit.RetrofitClientInstance;
import com.finlake.service.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    public UserRepository() {

    }

    public void getUsers(UserResponseInterface userResponseInterface) {
        UserService userService = RetrofitClientInstance.getInstance().create(UserService.class);
        String authToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb3VyYXZAZ21haWwuY29tIiwiaWF0IjoxNjg0ODY4MzMwLCJleHAiOjE2ODYzMDgzMzB9.VW2vz7zc7bOtNg7hYKpI2hZmyhwu3e5Q64Qv4TEY8s4";
        Call<List<UserResponse>> initiateLogin = userService.getAllUsers("Bearer " + authToken);
        initiateLogin.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserResponse>> call, @NonNull Response<List<UserResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        userResponseInterface.onResponse(response.body());
                    }
                } else {
                    Log.d("checkingcalls", "onChanged: Failure response from api" + response.message());
                    userResponseInterface.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserResponse>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
