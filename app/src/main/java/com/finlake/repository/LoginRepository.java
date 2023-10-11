package com.finlake.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.finlake.models.LoginBody;
import com.finlake.models.LoginResponse;
import com.finlake.interfaces.LoginResponseInterface;
import com.finlake.retorfit.RetrofitClientInstance;
import com.finlake.service.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    public LoginRepository() {
    }

    public void loginRemote(LoginBody loginBody, LoginResponseInterface loginResponseInterface) {
        LoginService loginService = RetrofitClientInstance.getInstance().create(LoginService.class);
        Call<LoginResponse> initiateLogin = loginService.login(loginBody);
        initiateLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("checkingcalls", "onChanged: Success response from api" + response.body().getToken());
                    loginResponseInterface.onResponse(response.body());
                } else {
                    Log.d("checkingcalls", "onChanged: Failure response from api" + response.message());
                    loginResponseInterface.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                loginResponseInterface.onFailure(new Throwable("Failed " + t.getLocalizedMessage()));
                t.printStackTrace();
            }
        });
    }

}
