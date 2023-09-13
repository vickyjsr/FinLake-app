package com.finlake.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.finlake.R;
import com.finlake.interfaces.OnClickFinanceRoomListener;
import com.finlake.models.FinanceRoomResponse;

import java.util.List;
import java.util.Objects;

public class FinanceRoomAdapter extends RecyclerView.Adapter<FinanceRoomAdapter.FinanceRoomViewHolder> {
    List<FinanceRoomResponse> financeRoomList;
    OnClickFinanceRoomListener onClickFinanceRoomListener;

    public FinanceRoomAdapter() {

    }

    public FinanceRoomAdapter(List<FinanceRoomResponse> financeRoomList, OnClickFinanceRoomListener onClickFinanceRoomListener) {
        this.financeRoomList = financeRoomList;
        this.onClickFinanceRoomListener = onClickFinanceRoomListener;
    }

    @NonNull
    @Override
    public FinanceRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FinanceRoomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.finance_room_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceRoomViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return financeRoomList.size();
    }

    public void setItems(List<FinanceRoomResponse> financeRoomList) {
        this.financeRoomList = financeRoomList;
    }

    public class FinanceRoomViewHolder extends RecyclerView.ViewHolder {

        TextView tv_room_name, tv_last_updated_at;
        ConstraintLayout cl;
        ImageView iv_room_type;

        public FinanceRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_room_name = itemView.findViewById(R.id.tv_room_name);
            tv_last_updated_at = itemView.findViewById(R.id.tv_last_updated_at);
            cl = itemView.findViewById(R.id.cl_layout);
            iv_room_type = itemView.findViewById(R.id.iv_room_type);
            cl.setOnClickListener(view -> onClickFinanceRoomListener.selectedFinanceRoomItem(financeRoomList.get(getAdapterPosition())));
        }

        public void setData(int position) {
            FinanceRoomResponse financeRoomResponse = financeRoomList.get(position);
            tv_room_name.setText(financeRoomResponse.getName());

            if (Objects.equals(financeRoomResponse.getRoom_type(), "GROUP")) {
                iv_room_type.setBackgroundResource(R.drawable.group_room);
                iv_room_type.setVisibility(View.VISIBLE);
            }
        }
    }
}
