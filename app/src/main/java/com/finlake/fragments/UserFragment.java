package com.finlake.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finlake.R;
import com.finlake.MyPreferences;
import com.finlake.activities.OnBoardingActivity;
import com.finlake.adapters.UserAdapter;
import com.finlake.enums.GlobalEnum;
import com.finlake.interfaces.OnBackPressFrag;
import com.finlake.interfaces.OnClickSelectionListener;
import com.finlake.models.FinanceRoomBody;
import com.finlake.models.FinanceRoomRequestData;
import com.finlake.models.FinanceRoomResponse;
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
    MyPreferences myPreferences;
    ImageView iv_done, iv_edit;
    OnBackPressFrag onBackPressFrag;
    EditText et_group_name;
    TextView tv_header;

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
        String authToken = myPreferences.getAuthToken();
        String userId = myPreferences.getLoggedInUserId();
        if (authToken == null || userId == null) {
            redirectToLoginPage(view);
        }

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_header.setVisibility(View.INVISIBLE);
                tv_header.setClickable(false);
                tv_header.setFocusable(false);
                et_group_name.setVisibility(View.VISIBLE);
            }
        });

        userViewModel.getAllUsers(authToken, userId);

        userViewModel.getAllUsersList().observe(getViewLifecycleOwner(), listUsers -> {
            userAdapter.setItems(listUsers);
            userAdapter.notifyDataSetChanged();
        });

        userViewModel.getTokenFailure().observe(getViewLifecycleOwner(), tokenFailure -> {
            if (tokenFailure) {
                if (this.getActivity() != null) {
                    myPreferences.setAuthToken(null);
                    startActivity(new Intent(this.getActivity(), OnBoardingActivity.class));
                    this.getActivity().finish();
                }
            }
        });

        iv_done.setOnClickListener(v -> {
            String room_type = GlobalEnum.ONE_ON_ONE.getValue();
            if (selectedUsers.size() >= 2) {
                room_type = GlobalEnum.GROUP.getValue();
            }
            if (et_group_name.getText() == null) {
                makeToast(view, "Group Name is empty");
            }
            FinanceRoomBody financeRoomBody = new FinanceRoomBody(et_group_name.getText().toString(), userId, room_type);
            if (!selectedUsers.isEmpty()) {
                FinanceRoomRequestData financeRoomRequestData = new FinanceRoomRequestData(financeRoomBody, selectedUsers, userId);
                roomViewModel.createFinanceRoom(authToken, financeRoomRequestData);
                onBackPressFrag.onBack();
            }
        });

        int page = 0;
        int pageSize = 10;
        boolean pagination = true;
        String status = "active";

        roomViewModel.getFinanceRoomResponse().observe(getViewLifecycleOwner(), new Observer<FinanceRoomResponse>() {
            @Override
            public void onChanged(FinanceRoomResponse financeRoomResponse) {
                roomViewModel.getAllFinanceRoomByUserId(page, pageSize, pagination, status, authToken, userId);
            }
        });

    }

    private void setUpViews(View view) {
        if (view.getContext() != null) {
            myPreferences = MyPreferences.getInstance(view.getContext());
            iv_done = view.findViewById(R.id.iv_done);
            iv_edit = view.findViewById(R.id.iv_edit);
            tv_header = view.findViewById(R.id.tv_header);
            et_group_name = view.findViewById(R.id.et_group_name);
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

    private void redirectToLoginPage(View view) {
        startActivity(new Intent(view.getContext(), OnBoardingActivity.class));
        onBackPressFrag.onBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void selectedItem(List<UserResponse> selectedUsers) {
        if (selectedUsers.size() >= 2) {
            this.selectedUsers = selectedUsers;
            iv_done.setVisibility(View.VISIBLE);
        } else {
            iv_done.setVisibility(View.GONE);
        }
    }

    private void makeToast(View view, String token) {
        Toast.makeText(view.getContext(), token, Toast.LENGTH_SHORT).show();
    }
}