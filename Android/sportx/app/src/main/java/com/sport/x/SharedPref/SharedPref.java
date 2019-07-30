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

    public void createLoginSession(String _id, String email, Integer role, String name, String number,String picture ) {
        editor.putBoolean("login", true);
        editor.putString("_id", _id);
        editor.putString("email", email);
        editor.putInt("role", role);
        editor.putString("name", name);
        editor.putString("number", number);
        editor.putString("picture", picture);
        editor.commit();
    }

    public String getUserId() {
        String _id = sharedPreferences.getString("_id", null);
        return _id;
    }

    public String getEmail() {
        String email = sharedPreferences.getString("email", null);
        return email;
    }

    public Integer getUserRole() {
        Integer role = sharedPreferences.getInt("role", 0);
        return role;
    }


    public String getName() {
        String name = sharedPreferences.getString("name", null);
        return name;
    }

    public String getNumber() {
        String number = sharedPreferences.getString("number", null);
        return number;
    }

    public String getPicture() {
        String picture = sharedPreferences.getString("picture", null);
        return picture;
    }


    public void clearSession() {
        editor.remove("_id");
        editor.remove("role");
        editor.remove("number");
        editor.remove("email");
        editor.remove("picture");
        editor.putBoolean("login", false);
        editor.clear();
        editor.commit();
    }

}
