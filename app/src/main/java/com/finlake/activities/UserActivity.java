package com.finlake.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.finlake.R;
import com.finlake.adapters.UserAdapter;
import com.finlake.models.UserResponse;
import com.finlake.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserResponse> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setUpViews();
        setUpListeners();
    }

    private void setUpListeners() {
        userViewModel.getAllUsers();

        userViewModel.getAllUsersList().observe(this, listUsers -> {
            Log.d("checkingcalls", "setUpListeners: " + listUsers);
            userAdapter.setItems(listUsers);
            this.userAdapter.notifyDataSetChanged();
        });
    }

    private void setUpViews() {
        recyclerView = findViewById(R.id.rv_list);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }
}
