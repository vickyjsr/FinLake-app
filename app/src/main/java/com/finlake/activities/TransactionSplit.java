package com.finlake.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.finlake.R;

public class TransactionSplit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_split);
        String finance_room_id = getIntent().getStringExtra("finance_room_id");
        Toast.makeText(this, finance_room_id, Toast.LENGTH_SHORT).show();



    }
}
