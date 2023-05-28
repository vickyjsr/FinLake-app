package com.finlake.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finlake.R;
import com.finlake.SharedPreferenceManager;
import com.finlake.viewmodels.OnBoardingViewModel;

public class OnBoardingActivity extends AppCompatActivity {

    ImageView iv_mascot;
    EditText et_email, et_password;
    Button login;
    TextView tv_token;
    OnBoardingViewModel mOnBoardingViewModel;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        setUpViews();
        listeners();
    }

    private void setUpViews() {
        sharedPreferenceManager = SharedPreferenceManager.getInstance();
        sharedPreferenceManager.init(this);

        iv_mascot = findViewById(R.id.iv_mascot);
        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        login = findViewById(R.id.b_login);
        tv_token = findViewById(R.id.tv_token);
        mOnBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);
    }

    private void listeners() {
        String authToken = sharedPreferenceManager.getAuthToken();
        if (authToken != null && !authToken.isEmpty() && !authToken.startsWith("Failed")) {
            startActivity(new Intent(this, UserActivity.class));
            finish();
        }

        login.setOnClickListener(view -> mOnBoardingViewModel.login(et_email.getText().toString(), et_password.getText().toString()));

        mOnBoardingViewModel.getLoginResult().observe(this, token -> {
            Log.d("checkingcalls", "onChanged: activity"+token);
            if (!token.isEmpty() && !token.startsWith("Failed")) {
                sharedPreferenceManager.setAuthToken(token);
                tv_token.setText(token);
                makeToast(token);
                startActivity(new Intent(this, UserActivity.class));
                finish();
            }
            makeToast(token);
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int passwordLength = editable.toString().length();
                switch (passwordLength) {
                    case 0:
                        iv_mascot.setBackgroundResource(R.drawable.yeti_mascot_1);
                        break;
                    case 1:
                        iv_mascot.setBackgroundResource(R.drawable.yeti_mascot_2);
                        break;
                    case 2:
                        iv_mascot.setBackgroundResource(R.drawable.yeti_mascot_3);
                        break;
                    default:
                        iv_mascot.setBackgroundResource(R.drawable.yeti_mascot_4);
                        break;
                }
            }
        });
    }

    private void makeToast(String token) {
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    }
}
