package com.finlake.adapters;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("meadosmckfvgweadc", "onClick: sfdj vk meadosmckfvgweadc");
                    final Dialog dialog = new Dialog(itemView.getContext());
                    dialog.setContentView(R.layout.tooltip_dropdown);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
        }
    }
}
