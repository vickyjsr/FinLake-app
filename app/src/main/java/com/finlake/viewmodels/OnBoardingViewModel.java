package com.finlake.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.finlake.models.LoginBody;
import com.finlake.models.LoginResponse;
import com.finlake.interfaces.LoginResponseInterface;
import com.finlake.repository.LoginRepository;

public class OnBoardingViewModel extends ViewModel {
    MutableLiveData<String> mLoginResultMutableLiveData = new MutableLiveData<>();

    LoginRepository mLoginRepository;

    public OnBoardingViewModel() {
        mLoginResultMutableLiveData.postValue("Not Logged in");
        // set the progress bar
        mLoginRepository = new LoginRepository();
    }

    public void login(String email, String password) {
        mLoginResultMutableLiveData.postValue("checking");

        mLoginRepository.loginRemote(new LoginBody(email, password), new LoginResponseInterface() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
//                set the progress bar
                mLoginResultMutableLiveData.postValue(loginResponse.getToken());
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLoginResultMutableLiveData.postValue("Login Failure: " + throwable.getLocalizedMessage());
            }
        });
    }

    public LiveData<String> getLoginResult() {
        return mLoginResultMutableLiveData;
    }
}
