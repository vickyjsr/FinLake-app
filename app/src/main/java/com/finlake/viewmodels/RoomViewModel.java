package com.finlake.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.finlake.interfaces.FinanceRoomInterface;
import com.finlake.models.FinanceRoomRequestData;
import com.finlake.models.FinanceRoomResponse;
import com.finlake.repository.FinanceRoomRepository;

import java.util.List;

public class RoomViewModel extends ViewModel {

    MutableLiveData<FinanceRoomResponse> financeMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<FinanceRoomResponse>> roomUserResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> mFailureResult = new MutableLiveData<>();
    MutableLiveData<Boolean> mTokenFailure = new MutableLiveData<>(false);
    FinanceRoomRepository financeRoomRepository;

    public RoomViewModel() {
        financeRoomRepository = new FinanceRoomRepository();
    }

    public void createFinanceRoom(String authToken, FinanceRoomRequestData financeRoomRequestData) {
        financeRoomRepository.createFinanceRoom(authToken, financeRoomRequestData, new FinanceRoomInterface() {
            @Override
            public void onResponse(FinanceRoomResponse financeRoomResponse) {
                financeMutableLiveData.postValue(financeRoomResponse);
            }

            @Override
            public void onResponseList(List<FinanceRoomResponse> financeRoomResponses) {

            }

            @Override
            public void onFailure(Throwable throwable) {
                mFailureResult.setValue(throwable.getLocalizedMessage());
            }

            @Override
            public void redirectToLogin() {
                mTokenFailure.postValue(true);
            }
        });
    }

    public void getAllFinanceRoomByUserId(int page, int pageSize, boolean pagination, String status, String authToken, String userId) {
        financeRoomRepository.getAllFinanceRoomByUserId(page, pageSize,pagination,status, authToken, userId, new FinanceRoomInterface() {
            @Override
            public void onResponse(FinanceRoomResponse financeRoomResponse) {

            }

            @Override
            public void onResponseList(List<FinanceRoomResponse> financeRoomResponses) {
                roomUserResponseMutableLiveData.postValue(financeRoomResponses);
            }

            @Override
            public void onFailure(Throwable throwable) {
                mFailureResult.setValue(throwable.getLocalizedMessage());
            }

            @Override
            public void redirectToLogin() {
                mTokenFailure.postValue(true);
            }
        });
    }

    public LiveData<FinanceRoomResponse> getFinanceRoomResponse() {
        return financeMutableLiveData;
    }

    public LiveData<List<FinanceRoomResponse>> getFinanceRoomByUserId() {
        return roomUserResponseMutableLiveData;
    }

    public LiveData<Boolean> getTokenFailure() {
        return mTokenFailure;
    }

    public LiveData<String> getErrorMessage() {
        return mFailureResult;
    }
}
