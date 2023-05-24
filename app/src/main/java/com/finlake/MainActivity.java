package com.finlake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.finlake.activities.OnBoardingActivity;
import com.finlake.activities.UserActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        change this
        startActivity(new Intent(this, UserActivity.class));
    }
}