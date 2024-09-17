package com.finlake.interfaces;

import com.finlake.models.LoginResponse;
import com.finlake.models.UserResponse;

import java.util.List;

public interface UserResponseInterface {
    void onResponse(List<UserResponse> userResponse);

    void onFailure(Throwable throwable);

    void redirectToLogin();
}
