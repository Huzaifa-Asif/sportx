package com.sport.x.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Context context;
    private static final String PREF_NAME = "SportxLog";
    private static final String IS_LOGIN = "IsLoggedIn";
    private int PRIVATE_MODE = 0;
    private String token;
    private String compareServiceProvider1;
    private String compareServiceProvider2;



    private String compareServiceProvider3;
    private String compareServiceProvider4;
    private boolean profileCompleted;
    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void createLoginSession(String _id, String email, String address, Integer role, String name, String contact,String picture ) {
        editor.putBoolean("login", true);
        editor.putString("_id", _id);
        editor.putString("email", email);
        editor.putInt("role", role);
        editor.putString("name", name);
        editor.putString("contact", contact);
        editor.putString("picture", picture);
        editor.putString("address", address);
        editor.commit();
    }
    public void setToken(String newToken)
    {
        editor.putString("token", newToken);
        editor.commit();

    }
    public String getToken()
    {
        return sharedPreferences.getString("token", null);
    }

    public void setProfileCompleted(boolean profileCompleted1)
    {
        editor.putBoolean("profileCompleted", profileCompleted1);
        editor.commit();

    }
    public boolean getProfileCompleted()
    {
        return sharedPreferences.getBoolean("profileCompleted", false);
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

    public String getAddress() {
        String address = sharedPreferences.getString("address", null);
        return address;
    }

    public String getContact() {
        String number = sharedPreferences.getString("contact", null);
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
        editor.remove("serviceProvider1");
        editor.remove("serviceProvider2");
        editor.remove("serviceProvider3");
        editor.remove("serviceProvider4");
        editor.putBoolean("login", false);
        editor.remove("spState");
        editor.clear();
        editor.commit();
    }

    public boolean getSpState()
    {
        return sharedPreferences.getBoolean("spState",false);
    }
    public void setSpState(boolean spState)
    {
        editor.putBoolean("spState",spState);
        editor.commit();
    }
    public String getCompareServiceProvider2() {
        return sharedPreferences.getString("compareServiceProvider2", null);
    }

    public void setCompareServiceProvider2(String compareServiceProvider2) {
        editor.putString("compareServiceProvider2", compareServiceProvider2);
        editor.commit();
    }

    public String getCompareServiceProvider1() {
        return sharedPreferences.getString("compareServiceProvider1", null);
    }

    public void setCompareServiceProvider1(String compareServiceProvider1) {
        editor.putString("compareServiceProvider1", compareServiceProvider1);
        editor.commit();
    }

    public String getCompareServiceProvider4() {
        return sharedPreferences.getString("compareServiceProvider4", null);
    }

    public void setCompareServiceProvider4(String compareServiceProvider4) {
        editor.putString("compareServiceProvider4", compareServiceProvider4);
        editor.commit();
    }

    public String getCompareServiceProvider3() {
        return sharedPreferences.getString("compareServiceProvider3", null);
    }

    public void setCompareServiceProvider3(String compareServiceProvider3) {
        editor.putString("compareServiceProvider3", compareServiceProvider3);
        editor.commit();
    }


}
