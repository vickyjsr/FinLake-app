package com.finlake.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.finlake.R;
import com.finlake.MyPreferences;
import com.finlake.adapters.UserAdapter;
import com.finlake.interfaces.OnClickSelectionListener;
import com.finlake.models.UserResponse;
import com.finlake.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements OnClickSelectionListener {

    UserViewModel userViewModel;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserResponse> userList;
    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setUpViews();
        setUpListeners();
    }

    private void setUpListeners() {
        String authToken = myPreferences.getAuthToken();
        String userId = myPreferences.getLoggedInUserId();
        if (authToken == null || userId == null) {
            redirectToLoginPage();
        }
        userViewModel.getAllUsers(authToken, userId);

        userViewModel.getAllUsersList().observe(this, listUsers -> {
            Log.d("checkingcalls", "setUpListeners: " + listUsers);
            userAdapter.setItems(listUsers);
            userAdapter.notifyDataSetChanged();
        });

        userViewModel.getTokenFailure().observe(this, tokenFailure -> {
            if (tokenFailure) {
                myPreferences.setAuthToken(null);
                startActivity(new Intent(this, OnBoardingActivity.class));
                finish();
            }
        });

    }

    private void setUpViews() {
        myPreferences = MyPreferences.getInstance(this);

        recyclerView = findViewById(R.id.rv_list);
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void redirectToLoginPage() {
        startActivity(new Intent(this, OnBoardingActivity.class));
        finish();
    }

    @Override
    public void selectedItem(List<UserResponse> selectedUsers) {

    }
}
