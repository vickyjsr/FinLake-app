package com.finlake.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.finlake.R;
import com.finlake.MyPreferences;
import com.finlake.adapters.FinanceChatHeadAdapter;
import com.finlake.adapters.FinanceRoomAdapter;
import com.finlake.fragments.UserFragment;
import com.finlake.interfaces.OnBackPressFrag;
import com.finlake.interfaces.OnClickFinanceRoomListener;
import com.finlake.interfaces.PaginationScrollListener;
import com.finlake.models.FinanceRoomResponse;
import com.finlake.viewmodels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class FinanceRoomActivity extends AppCompatActivity implements OnBackPressFrag, OnClickFinanceRoomListener {

    ConstraintLayout container;
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
    ImageView iv_refresh, iv_logout;
    ObjectAnimator rotation;
    LinearLayoutManager vertical_linear_layout_manager;
    boolean isLoading = false;
    int currentPage = 0;
    boolean isScrolling = false;
    private boolean isLastPage = false;
    private int visibleItems, totalItems, scrollOutItems;

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
        container = findViewById(R.id.container);
        iv_logout = findViewById(R.id.iv_logout);
        iv_refresh = findViewById(R.id.iv_refresh);
        rv_finance_room = findViewById(R.id.rv_finance_room);
        rv_finance_chat_head = findViewById(R.id.rv_split_heads);
        financeRoomList = new ArrayList<>();
        financeRoomAdapter = new FinanceRoomAdapter(financeRoomList, this);
//        todo change this
        financeChatHeadAdapter = new FinanceChatHeadAdapter();
        rv_finance_room.setHasFixedSize(true);
        vertical_linear_layout_manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_finance_room.setLayoutManager(vertical_linear_layout_manager);
        rv_finance_room.setAdapter(financeRoomAdapter);
        rv_finance_chat_head.setHasFixedSize(true);
        rv_finance_chat_head.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_finance_chat_head.setAdapter(financeChatHeadAdapter);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
    }

    private void setUpListeners() {
        iv_logout.setOnClickListener(view -> {
            myPreferences.setAuthToken(null);
            myPreferences.setLoggedInUserId(null);
            makeToast("Logging out...");
            redirectToLoginPage();
        });

        iv_refresh.setOnClickListener(view -> setAllViewModels(true));

        iv_plus.setOnClickListener(view -> {
            iv_plus.setVisibility(View.GONE);
            iv_refresh.setClickable(false);
            iv_logout.setClickable(false);
            fragment = UserFragment.getInstance(this);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.add(R.id.container, fragment).commit();
        });

        roomViewModel.getFinanceRoomByUserId().observe(this, new Observer<List<FinanceRoomResponse>>() {
            @Override
            public void onChanged(List<FinanceRoomResponse> financeRoomResponses) {
                new Handler().postDelayed(() -> {
                    if (rotation != null) {
                        iv_refresh.setBackgroundResource(R.drawable.refresh);
                        rotation.cancel();
                    }
                }, 2000);
                int oldCount = financeRoomList.size();
                financeRoomList.addAll(financeRoomResponses);
                financeRoomAdapter.notifyDataSetChanged();
            }
        });

        roomViewModel.getErrorMessage().observe(this, s -> {
            Toast.makeText(this, "failed " + s, Toast.LENGTH_SHORT).show();
        });

        roomViewModel.getTokenFailure().observe(this, b -> {
            if (b) {
                redirectToLoginPage();
            }
        });

        loadFirstPage();

        rv_finance_room.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItems = vertical_linear_layout_manager.getChildCount();
                totalItems = vertical_linear_layout_manager.getItemCount();
                scrollOutItems = vertical_linear_layout_manager.findFirstVisibleItemPosition();
                boolean isNotLoadingAndNotLastPage = !isLoading && !isLastPage;
                boolean isAtLastItem = scrollOutItems + visibleItems >= totalItems;
                boolean isNotAtBeginning = scrollOutItems >= 0;
                boolean isTotalMoreThanVisible = totalItems >= 10;
                boolean shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling;
                if (shouldPaginate && !recyclerView.canScrollVertically(1)) {
                    isScrolling = false;
                    currentPage += 1;
                    loadNextPage(currentPage, 10, true, "active", authToken, userId);
                }
            }
        });
    }

    private void loadFirstPage() {
        int page = 0;
        int pageSize = 10;
        boolean pagination = true;
        String status = "active";
        roomViewModel.getAllFinanceRoomByUserId(page, pageSize, pagination, status, authToken, userId);
    }

    private void loadNextPage(int currentPage, int pageSize, boolean pagination, String status, String authToken, String userId) {
        Log.d("jfbnfvskedn", "loadNextPage: " + currentPage);
        roomViewModel.getAllFinanceRoomByUserId(currentPage, pageSize, pagination, status, authToken, userId);
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
            iv_refresh.setClickable(true);
            iv_logout.setClickable(true);
            fragment = null;
        } else {
            super.onBackPressed(); // Allow the activity to be closed if no fragment is added
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAllViewModels(true);
    }

    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void selectedFinanceRoomItem(FinanceRoomResponse financeRoomResponse) {
        Intent intent = new Intent(this, TransactionChat.class);
        intent.putExtra("finance_room_id", financeRoomResponse.getId());
        startActivity(intent);
    }

    public void setAllViewModels(boolean animateLoader) {
        financeRoomList.clear();
        if (animateLoader) {
            iv_refresh.setBackgroundResource(R.drawable.refresh_animation_state);
            rotation = ObjectAnimator.ofFloat(iv_refresh, "rotation", 0f, 360f);
            rotation.setDuration(3000); // 3 seconds for one complete rotation
            rotation.setRepeatCount(ObjectAnimator.INFINITE);
            rotation.setInterpolator(new LinearInterpolator());
            rotation.start();
        }
        int page = 0;
        int pageSize = 10;
        boolean pagination = true;
        String status = "active";
        currentPage = 0;
        roomViewModel.getAllFinanceRoomByUserId(page, pageSize, pagination, status, authToken, userId);
    }

    private void makeToast(String token) {
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    }
}
