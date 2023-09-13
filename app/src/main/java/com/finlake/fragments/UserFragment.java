package com.finlake.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.finlake.R;
import com.finlake.SharedPreferenceManager;
import com.finlake.activities.OnBoardingActivity;
import com.finlake.adapters.UserAdapter;
import com.finlake.interfaces.OnBackPressFrag;
import com.finlake.interfaces.OnClickSelectionListener;
import com.finlake.models.FinanceRoomBody;
import com.finlake.models.FinanceRoomRequestData;
import com.finlake.models.FinanceRoomResponse;
import com.finlake.models.RoomUserResponse;
import com.finlake.models.UserResponse;
import com.finlake.viewmodels.RoomViewModel;
import com.finlake.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment implements OnClickSelectionListener {

    public static UserFragment userFragment;
    UserViewModel userViewModel;
    RoomViewModel roomViewModel;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserResponse> userList, selectedUsers;
    SharedPreferenceManager sharedPreferenceManager;
    ImageView iv_done;
    OnBackPressFrag onBackPressFrag;

    public UserFragment(OnBackPressFrag onBackPressFrag) {
        this.onBackPressFrag = onBackPressFrag;
    }

    public static Fragment getInstance(OnBackPressFrag onBackPressFrag) {
        if (userFragment == null) {
            userFragment = new UserFragment(onBackPressFrag);
        }
        return userFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        setUpViews(view);
        setUpListeners(view);
        return view;
    }

    private void setUpListeners(View view) {
        String authToken = sharedPreferenceManager.getAuthToken();
        userViewModel.getAllUsers(authToken);

        userViewModel.getAllUsersList().observe(getViewLifecycleOwner(), listUsers -> {
            Log.d("checkingcalls", "setUpListeners: " + listUsers);
            userAdapter.setItems(listUsers);
            userAdapter.notifyDataSetChanged();
        });

        userViewModel.getTokenFailure().observe(getViewLifecycleOwner(), tokenFailure -> {
            if (tokenFailure) {
                if (this.getActivity() != null) {
                    sharedPreferenceManager.setAuthToken(null);
                    startActivity(new Intent(this.getActivity(), OnBoardingActivity.class));
                    this.getActivity().finish();
                }
            }
        });

        iv_done.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "skjznvkdhjk hjvaskncj", Toast.LENGTH_SHORT).show();
            FinanceRoomBody financeRoomBody = new FinanceRoomBody("xyz", "dd78fd8a-8006-4ad5-a82a-3c499c2b08e4", "group");
            if (!selectedUsers.isEmpty()) {
                FinanceRoomRequestData financeRoomRequestData = new FinanceRoomRequestData(financeRoomBody, selectedUsers);
                roomViewModel.createFinanceRoom(authToken, financeRoomRequestData);
                onBackPressFrag.onBack();
            }
        });

        roomViewModel.getFinanceRoomResponse().observe(getViewLifecycleOwner(), new Observer<FinanceRoomResponse>() {
            @Override
            public void onChanged(FinanceRoomResponse financeRoomResponse) {
                roomViewModel.getAllFinanceRoomByUserId(authToken, "dd78fd8a-8006-4ad5-a82a-3c499c2b08e4");
            }
        });

    }

    private void setUpViews(View view) {
        if (view.getContext() != null) {
            sharedPreferenceManager = SharedPreferenceManager.getInstance();
            sharedPreferenceManager.init(view.getContext());
            iv_done = view.findViewById(R.id.iv_done);
            recyclerView = view.findViewById(R.id.rv_list);
            userList = new ArrayList<>();
            selectedUsers = new ArrayList<>();
            userAdapter = new UserAdapter(userList, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(userAdapter);
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void selectedItem(List<UserResponse> selectedUsers) {
        if (selectedUsers.size() > 2) {
            this.selectedUsers = selectedUsers;
            iv_done.setVisibility(View.VISIBLE);
        } else {
            this.selectedUsers.clear();
            iv_done.setVisibility(View.GONE);
        }
    }

}