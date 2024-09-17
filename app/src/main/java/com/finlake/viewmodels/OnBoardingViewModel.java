package com.finlake.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.finlake.Constants.Constant;
import com.finlake.models.LoginBody;
import com.finlake.models.LoginResponse;
import com.finlake.interfaces.LoginResponseInterface;
import com.finlake.repository.LoginRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class OnBoardingViewModel extends ViewModel {
    MutableLiveData<HashMap<String, String>> mLoginResultMutableLiveData = new MutableLiveData<>();

    LoginRepository mLoginRepository;

    public OnBoardingViewModel() {
        // set the progress bar
        mLoginRepository = new LoginRepository();
    }

    public void login(String email, String password) {
        String requestId = UUID.randomUUID().toString();

        mLoginRepository.loginRemote(new LoginBody(requestId, email, password), new LoginResponseInterface() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
//                set the progress bar
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Constant.AUTH_TOKEN, loginResponse.getData().getToken());
                hashMap.put(Constant.LOGGED_IN_USER_ID, loginResponse.getData().getUserId());
                mLoginResultMutableLiveData.postValue(hashMap);
                Log.d("checkingcalls", "onResponse: jkhxcvbhncnvb" + loginResponse.getErrorMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("checkingcalls", "onfailure: jkhxcvbhncnvb" + throwable.getLocalizedMessage());
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("error", throwable.getLocalizedMessage());
                mLoginResultMutableLiveData.postValue(hashMap);
            }
        });
    }

    public LiveData<HashMap<String, String>> getLoginResult() {
        return mLoginResultMutableLiveData;
    }
}
