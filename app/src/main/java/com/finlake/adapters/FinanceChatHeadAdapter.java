package com.finlake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finlake.R;

public class FinanceChatHeadAdapter extends RecyclerView.Adapter<FinanceChatHeadAdapter.FinanceChatHeadViewHolder> {

    @NonNull
    @Override
    public FinanceChatHeadAdapter.FinanceChatHeadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FinanceChatHeadAdapter.FinanceChatHeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.finance_chat_head_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceChatHeadAdapter.FinanceChatHeadViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class FinanceChatHeadViewHolder extends RecyclerView.ViewHolder {

        public FinanceChatHeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
