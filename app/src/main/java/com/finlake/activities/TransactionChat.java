package com.finlake.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.finlake.R;

public class TransactionChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_chat);
        String finance_room_id = getIntent().getStringExtra("finance_room_id");
        Toast.makeText(this, finance_room_id, Toast.LENGTH_SHORT).show();


    }
}
