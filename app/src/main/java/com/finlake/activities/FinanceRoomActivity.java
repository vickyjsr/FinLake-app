package com.finlake.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.finlake.R;
import com.finlake.MyPreferences;
import com.finlake.adapters.FinanceChatHeadAdapter;
import com.finlake.adapters.FinanceRoomAdapter;
import com.finlake.fragments.UserFragment;
import com.finlake.interfaces.OnBackPressFrag;
import com.finlake.interfaces.OnClickFinanceRoomListener;
import com.finlake.models.FinanceRoomResponse;
import com.finlake.viewmodels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class FinanceRoomActivity extends AppCompatActivity implements OnBackPressFrag, OnClickFinanceRoomListener {

    ImageView iv_plus;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    RoomViewModel roomViewModel;
    MyPreferences myPreferences;
    String authToken;
    String userId;
    List<FinanceRoomResponse> financeRoomList;

    RecyclerView rv_finance_room, rv_finance_chat_head;
    FinanceRoomAdapter financeRoomAdapter;
    FinanceChatHeadAdapter financeChatHeadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_room);
        setUpViews();
        setUpListeners();
    }

    private void setUpViews() {
        iv_plus = findViewById(R.id.iv_plus);
        myPreferences = MyPreferences.getInstance(this);
        authToken = myPreferences.getAuthToken();
        userId = myPreferences.getLoggedInUserId();
        if (authToken == null || userId == null) {
            redirectToLoginPage();
        }
        rv_finance_room = findViewById(R.id.rv_finance_room);
        rv_finance_chat_head = findViewById(R.id.rv_split_heads);
        financeRoomList = new ArrayList<>();
        financeRoomAdapter = new FinanceRoomAdapter(financeRoomList, this);
//        todo change this
        financeChatHeadAdapter = new FinanceChatHeadAdapter();
        rv_finance_room.setHasFixedSize(true);
        rv_finance_room.setLayoutManager(new LinearLayoutManager(this));
        rv_finance_room.setAdapter(financeRoomAdapter);
        rv_finance_chat_head.setHasFixedSize(true);
        rv_finance_chat_head.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_finance_chat_head.setAdapter(financeChatHeadAdapter);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
    }

    private void setUpListeners() {
        iv_plus.setOnClickListener(view -> {
            iv_plus.setVisibility(View.GONE);
            fragment = UserFragment.getInstance(this);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, fragment).commit();
        });

        int page = 0;
        int pageSize = 10;
        boolean pagination = true;
        String status = "active";
        roomViewModel.getAllFinanceRoomByUserId(page, pageSize, pagination, status, authToken, userId);

        roomViewModel.getFinanceRoomByUserId().observe(this, financeRoomResponses -> {
            financeRoomAdapter.setItems(financeRoomResponses);
            financeRoomAdapter.notifyDataSetChanged();
        });

        roomViewModel.getErrorMessage().observe(this, s -> {
            Toast.makeText(this, "failed " + s, Toast.LENGTH_SHORT).show();
        });

        roomViewModel.getTokenFailure().observe(this, b -> {
            if (b) {
                redirectToLoginPage();
            }
        });
    }

    private void redirectToLoginPage() {
        startActivity(new Intent(this, OnBoardingActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (fragment != null && fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            iv_plus.setVisibility(View.VISIBLE);
            fragment = null;
        } else {
            super.onBackPressed(); // Allow the activity to be closed if no fragment is added
        }
    }

    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void selectedFinanceRoomItem(FinanceRoomResponse financeRoomResponse) {
        Intent intent = new Intent(this, TransactionSplit.class);
        intent.putExtra("finance_room_id", financeRoomResponse.getId());
        startActivity(intent);
    }
}
