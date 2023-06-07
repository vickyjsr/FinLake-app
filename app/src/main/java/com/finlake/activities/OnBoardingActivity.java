package com.finlake.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.finlake.R;
import com.finlake.SharedPreferenceManager;
import com.finlake.viewmodels.OnBoardingViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class OnBoardingActivity extends AppCompatActivity {

    ImageView iv_mascot, iv_shutter;
    View layout;
    TextInputEditText et_email, et_password;
    TextInputLayout password_toggle;
    Button login;
    TextView tv_token;
    OnBoardingViewModel mOnBoardingViewModel;
    SharedPreferenceManager sharedPreferenceManager;
    boolean shutter_up = true, toggle_shutter_up = true, password_visible = false;

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

        layout = findViewById(R.id.iv_mascot);
        iv_shutter = layout.findViewById(R.id.iv_shutter);
//        iv_mascot = findViewById(R.id.iv_mascot);
        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        password_toggle = findViewById(R.id.password_toggle);
        login = findViewById(R.id.b_login);
        tv_token = findViewById(R.id.tv_token);
        mOnBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);
    }

    private void listeners() {
        String authToken = sharedPreferenceManager.getAuthToken();
        if (authToken != null && !authToken.isEmpty() && !authToken.startsWith("Failed")) {
            startActivity(new Intent(this, FinanceRoomActivity.class));
            finish();
        }

        login.setOnClickListener(view -> mOnBoardingViewModel.login(et_email.getText().toString(), et_password.getText().toString()));

//        this method is not the correct way
        make_shutter_animation(740, false, 1);
        iv_shutter.setVisibility(View.VISIBLE);

        mOnBoardingViewModel.getLoginResult().observe(this, token -> {
            Log.d("checkingcalls", "onChanged: activity" + token);
            if (!token.isEmpty() && !token.startsWith("Failed")) {
                sharedPreferenceManager.setAuthToken(token);
                tv_token.setText(token);
                makeToast(token);
                startActivity(new Intent(this, FinanceRoomActivity.class));
                finish();
            }
            makeToast(token);
        });

        et_password.setOnFocusChangeListener((view, hasFocus) -> {
            if (password_visible) {
                Log.d("jnacskmncsnms", "listeners: 11" + shutter_up + "kjnlmcmsk" + password_visible);
            } else {
                Log.d("jnacskmncsnms", "listeners: 111 going in ??" + shutter_up + "kjnlmcmsk" + password_visible);
                int viewHeight = iv_shutter.getHeight();
                iv_shutter.setVisibility(View.VISIBLE);
                make_shutter_animation(viewHeight, shutter_up, 1000);
                shutter_up = !shutter_up;
            }
        });

//        password_toggle.setEndIconOnClickListener(view -> {
//            int type;
//            et_password.setFocusable(true);
//            iv_shutter.setVisibility(View.VISIBLE);
//            if (!password_visible) {
//                type = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
//            } else {
//                type = InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT;
//            }
//            et_password.setInputType(type);
//            password_visible = !password_visible;
//            Log.d("jnacskmncsnms", "listeners: 22" + shutter_up + "kjnlmcmsk" + password_visible);
//            if (!shutter_up) {
//                return;
//            }
//            int viewHeight = iv_shutter.getHeight();
//            make_shutter_animation(viewHeight, shutter_up, 1000);
//            shutter_up = !shutter_up;
//        });

    }

    private void make_shutter_animation(int viewHeight, boolean shutter, int milli_sec) {
        ObjectAnimator animator;
        if (shutter) {
            animator = ObjectAnimator.ofFloat(iv_shutter, "translationY", -viewHeight, 0);
        } else {
            animator = ObjectAnimator.ofFloat(iv_shutter, "translationY", 0, -viewHeight);
        }
        animator.setDuration(milli_sec);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void makeToast(String token) {
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    }
}
