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

    public void getUsers(String authToken, String id, UserResponseInterface userResponseInterface) {
        UserService userService = RetrofitClientInstance.getInstance().create(UserService.class);
        Call<List<UserResponse>> initiateLogin = userService.getAllUsersFiltered("Bearer " + authToken, id);
        initiateLogin.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserResponse>> call, @NonNull Response<List<UserResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userResponseInterface.onResponse(response.body());
                } else if (response.code() == 401) {
                    userResponseInterface.redirectToLogin();
                } else {
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
