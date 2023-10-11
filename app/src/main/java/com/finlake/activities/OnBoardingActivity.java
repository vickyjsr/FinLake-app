package com.finlake.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.finlake.Constants.Constant;
import com.finlake.R;
import com.finlake.MyPreferences;
import com.finlake.viewmodels.OnBoardingViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class OnBoardingActivity extends AppCompatActivity {

    ImageView iv_bear, iv_shutter;
    View layout;
    EditText et_email, et_password;
    TextInputLayout password_toggle;
    Button login;
    OnBoardingViewModel mOnBoardingViewModel;
    MyPreferences myPreferences;
    boolean shutter_up = true, toggle_shutter_up = true, password_visible = false;
    private final int[] svgResources = {R.drawable.yeti_mascot_1, R.drawable.yeti_mascot_4};
    private int toggle_eyes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        setUpViews();
        listeners();
    }

    private void setUpViews() {
        myPreferences = MyPreferences.getInstance(this);
        layout = findViewById(R.id.iv_mascot);
        iv_shutter = layout.findViewById(R.id.iv_shutter);
        iv_bear = layout.findViewById(R.id.iv_bear);
        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        login = findViewById(R.id.b_login);

        ValueAnimator blinkAnimator = createBlinkAnimator();
        blinkAnimator.start();

        mOnBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);
    }

    private void listeners() {
        String authToken = myPreferences.getAuthToken();
        String userId = myPreferences.getLoggedInUserId();
        if (userId != null && authToken != null && !authToken.isEmpty() && !authToken.startsWith("Failed")) {
            startActivity(new Intent(this, FinanceRoomActivity.class));
            finish();
        }

        login.setOnClickListener(view -> {
            if (et_email.getText() != null && et_password.getText() != null) {
                mOnBoardingViewModel.login(et_email.getText().toString(), et_password.getText().toString());
            }
        });

//        this method is not the correct way
        make_shutter_animation(740, false, 1);
        iv_shutter.setVisibility(View.VISIBLE);

        mOnBoardingViewModel.getLoginResult().observe(this, hashMap -> {
            Log.d("checkinwsdsgcalls", "listeners: " + hashMap.keySet());
            String newAuthToken = hashMap.get(Constant.AUTH_TOKEN);
            String newUserId = hashMap.get(Constant.USER_ID);
            Log.d("checkinwsdsgcalls", "onChanged: activity newAuthToken: " + newAuthToken + "      userId: " + newUserId);
            if (newAuthToken != null && !newAuthToken.isEmpty() && !newAuthToken.startsWith("Failed")) {
                myPreferences.setAuthToken(newAuthToken);
                myPreferences.setLoggedInUserId(newUserId);
                startActivity(new Intent(this, FinanceRoomActivity.class));
                finish();
            }
        });

        et_password.setOnFocusChangeListener((view, hasFocus) -> {
            if (!password_visible) {
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
//            shutter_up = !shutter_up;
//            if (!shutter_up) {
//                return;
//            }
//            int viewHeight = iv_shutter.getHeight();
//            make_shutter_animation(viewHeight, shutter_up, 1000);
//        });
    }

    private ValueAnimator createBlinkAnimator() {
        // Create a ValueAnimator for the blink effect
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            if (progress < 0.5f) {
                // Show open eyes
                iv_bear.setImageResource(svgResources[1]);
            } else {
                // Show closed eyes
                iv_bear.setImageResource(svgResources[0]);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation start logic
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation end logic
                // Start the next blink with a delay of 3 seconds (adjust as needed)
                iv_bear.postDelayed(() -> {
                    Random random = new Random();
                    int repeatCount = random.nextInt(3); // Generates a random number between 0 and 2
                    animator.setRepeatCount(repeatCount);
                    animator.start();
                }, 3000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation cancel logic
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeat logic
            }
        });

        return animator;
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
