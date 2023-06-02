package com.finlake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.finlake.R;
import com.finlake.interfaces.OnClickFinanceRoomListener;
import com.finlake.models.FinanceRoomResponse;

import java.util.List;

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

        TextView tv_room_name, tv_room_type;
        ConstraintLayout cl;

        public FinanceRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_room_name = itemView.findViewById(R.id.tv_room_name);
            tv_room_type = itemView.findViewById(R.id.tv_room_type);
            cl = itemView.findViewById(R.id.cl_layout);
            cl.setOnClickListener(view -> onClickFinanceRoomListener.selectedFinanceRoomItem(financeRoomList.get(getAdapterPosition())));
        }

        public void setData(int position) {
            FinanceRoomResponse financeRoomResponse = financeRoomList.get(position);
            tv_room_name.setText(financeRoomResponse.getName());
            tv_room_type.setText(financeRoomResponse.getRoom_type());
        }
    }
}
