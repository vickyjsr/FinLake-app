package com.finlake.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.finlake.interfaces.UserResponseInterface;
import com.finlake.models.UserResponse;
import com.finlake.models.response.UserListResponse;
import com.finlake.retorfit.RetrofitClientInstance;
import com.finlake.service.UserService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    public UserRepository() {

    }

    public void getUsers(String authToken, String requestId, String id, int page, int pageSize, UserResponseInterface userResponseInterface) {
        UserService userService = RetrofitClientInstance.getInstance().create(UserService.class);
        List<String> userIds = new ArrayList(Collections.singleton(id));
        Call<UserListResponse> initiateLogin = userService.getAllUsersFiltered(authToken, requestId, userIds, page, pageSize);
        initiateLogin.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserListResponse> call, @NonNull Response<UserListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserListResponse userListResponse = response.body();
                    List<UserResponse> userResponses = userListResponse.getData().getContent();
                    userResponseInterface.onResponse(userResponses);
                } else if (response.code() == 401) {
                    userResponseInterface.redirectToLogin();
                } else {
                    userResponseInterface.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
