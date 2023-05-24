package com.finlake.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.finlake.R;
import com.finlake.viewmodels.OnBoardingViewModel;

public class OnBoardingActivity extends AppCompatActivity {

    EditText et_email, et_password;
    Button login;
    TextView tv_token;
    OnBoardingViewModel mOnBoardingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        setUpViews();
        listeners();
    }

    private void setUpViews() {
        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        login = findViewById(R.id.b_login);
        tv_token = findViewById(R.id.tv_token);
        mOnBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);
    }

    private void listeners() {
        login.setOnClickListener(view -> mOnBoardingViewModel.login(et_email.getText().toString(), et_password.getText().toString()));

        mOnBoardingViewModel.getLoginResult().observe(this, token -> {
            Log.d("checkingcalls", "onChanged: activity");
            tv_token.setText(token);
        });
    }
}
