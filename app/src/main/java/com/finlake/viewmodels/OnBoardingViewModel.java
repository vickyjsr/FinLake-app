package com.finlake.viewmodels;

import android.util.Log;

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
        // set the progress bar
        mLoginRepository = new LoginRepository();
    }

    public void login(String email, String password) {

        mLoginRepository.loginRemote(new LoginBody(email, password), new LoginResponseInterface() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
//                set the progress bar
                mLoginResultMutableLiveData.postValue(loginResponse.getToken());
                Log.d("checkingcalls", "onResponse: jkhxcvbhncnvb"+loginResponse.getErrorMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("checkingcalls", "onfailure: jkhxcvbhncnvb"+throwable.getLocalizedMessage());
                mLoginResultMutableLiveData.postValue(throwable.getLocalizedMessage());
            }
        });
    }

    public LiveData<String> getLoginResult() {
        return mLoginResultMutableLiveData;
    }
}
