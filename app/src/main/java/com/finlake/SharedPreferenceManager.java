package com.finlake;

import android.content.Context;
import android.content.SharedPreferences;

import com.finlake.Constants.Constant;

public class SharedPreferenceManager {

    public static SharedPreferenceManager sharedPreferenceManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static SharedPreferenceManager getInstance() {
        if (sharedPreferenceManager == null) {
            sharedPreferenceManager = new SharedPreferenceManager();
        }
        return sharedPreferenceManager;
    }

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(Constant.APP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setAuthToken(String authToken) {
        editor.putString(Constant.AUTH_TOKEN, authToken);
        editor.commit();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(Constant.AUTH_TOKEN, null);
    }
}
