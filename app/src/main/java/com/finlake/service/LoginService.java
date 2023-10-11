package com.finlake.service;

import com.finlake.models.LoginBody;
import com.finlake.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("login")
    Call<LoginResponse> login(@Body LoginBody loginBody);
}
