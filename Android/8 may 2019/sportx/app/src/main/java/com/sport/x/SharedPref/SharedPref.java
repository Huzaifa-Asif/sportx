package com.sport.x.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Context context;
    private static final String PREF_NAME = "WIVAALog";
    private static final String IS_LOGIN = "IsLoggedIn";
    int PRIVATE_MODE = 0;

    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String userId, String userRole) {
        editor.putBoolean("login", true);
        editor.putString("userId", userId);
        editor.putString("userRole", userRole);

        editor.commit();
    }

    public String getUserId() {
        String userId = sharedPreferences.getString("userId", null);
        return userId;
    }

    public String getUserRole() {
        String userRole = sharedPreferences.getString("userRole", null);
        return userRole;
    }

    public void clearSession() {
        editor.remove("userId");
        editor.remove("userRole");
        editor.putBoolean("login", false);
        editor.clear();
        editor.commit();
    }

}
