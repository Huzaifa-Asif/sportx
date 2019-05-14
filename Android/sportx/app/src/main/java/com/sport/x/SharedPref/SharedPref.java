package com.sport.x.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Context context;
    private static final String PREF_NAME = "SportxLog";
    private static final String IS_LOGIN = "IsLoggedIn";
    int PRIVATE_MODE = 0;

    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String _id, Integer role) {
        editor.putBoolean("login", true);
        editor.putString("_id", _id);
        editor.putInt("role", role);

        editor.commit();
    }

    public String getUserId() {
        String _id = sharedPreferences.getString("_id", null);
        return _id;
    }

    public Integer getUserRole() {
        Integer role = sharedPreferences.getInt("role", 0);
        return role;
    }

    public void clearSession() {
        editor.remove("_id");
        editor.remove("role");
        editor.putBoolean("login", false);
        editor.clear();
        editor.commit();
    }

}
