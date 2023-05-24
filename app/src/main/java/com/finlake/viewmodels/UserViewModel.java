package com.finlake.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.finlake.interfaces.UserResponseInterface;
import com.finlake.models.UserResponse;
import com.finlake.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {

    MutableLiveData<List<UserResponse>> listMutableLiveData = new MutableLiveData<>();

    UserRepository userRepository;

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public void getAllUsers() {
        userRepository.getUsers(new UserResponseInterface() {
            @Override
            public void onResponse(List<UserResponse> userResponse) {
                listMutableLiveData.postValue(userResponse);
            }

            @Override
            public void onFailure(Throwable throwable) {
//                error message
            }
        });
    }

    public LiveData<List<UserResponse>> getAllUsersList() {
        return listMutableLiveData;
    }
}
