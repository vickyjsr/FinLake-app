package com.finlake.interfaces;

import com.finlake.models.LoginResponse;

public interface LoginResponseInterface {
    void onResponse(LoginResponse loginResponse);

    void onFailure(Throwable throwable);
}
