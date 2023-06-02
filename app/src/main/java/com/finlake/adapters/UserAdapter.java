package com.finlake.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finlake.R;
import com.finlake.interfaces.OnClickSelectionListener;
import com.finlake.models.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    List<UserResponse> listUsers;
    List<UserResponse> selectedUsers = new ArrayList<>();
    OnClickSelectionListener onClickSelectionListener;

    public UserAdapter(List<UserResponse> listUsers) {
        this.listUsers = listUsers;
    }

    public UserAdapter(List<UserResponse> listUsers, OnClickSelectionListener onClickSelectionListener) {
        this.listUsers = listUsers;
        this.onClickSelectionListener = onClickSelectionListener;
    }

    public void setItems(List<UserResponse> listUsers) {
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(position);
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView email, mobile, name;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.tv_email);
            mobile = itemView.findViewById(R.id.tv_mobile);
            name = itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(view -> {
                if (selectedUsers.contains(listUsers.get(getAdapterPosition()))) {
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                    selectedUsers.remove(listUsers.get(getAdapterPosition()));
                } else {
                    itemView.setBackgroundResource(R.color.purple_200);
                    selectedUsers.add(listUsers.get(getAdapterPosition()));
                }
                if (!selectedUsers.isEmpty())
                    onClickSelectionListener.selectedItem(selectedUsers);
            });

        }

        public void setUserData(int position) {
            UserResponse userResponse = listUsers.get(position);
            Log.d("checkingcalls", "setUserData: " + userResponse);
            email.setText(userResponse.getEmail());
            name.setText(userResponse.getName());
            mobile.setText(userResponse.getMobile_number());
        }
    }
}
