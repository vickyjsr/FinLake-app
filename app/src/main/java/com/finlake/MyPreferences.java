package com.finlake;

import android.content.Context;
import android.content.SharedPreferences;

import com.finlake.Constants.Constant;

public class MyPreferences {

    private static MyPreferences myPreferences;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Constant.APP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static MyPreferences getInstance(Context context) {
        if (myPreferences == null) myPreferences = new MyPreferences(context);
        return myPreferences;
    }

    public void setLoggedInUserId(String userId) {
        editor.putString(Constant.LOGGED_IN_USER_ID, userId);
        editor.commit();
    }

    public String getLoggedInUserId() {
        return sharedPreferences.getString(Constant.LOGGED_IN_USER_ID, null); // dont return any string always return null its easy to tackle than a string
    }

    public void setAuthToken(String authToken) {
        editor.putString(Constant.AUTH_TOKEN, authToken);
        editor.commit();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(Constant.AUTH_TOKEN, null);
    }
}



