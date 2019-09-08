package com.sport.x.notifications;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sport.x.Misc.Misc;

import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

public class Notifications extends AppCompatActivity {
 SharedPref SharedPref;
 Misc misc;
 Context context;

private  String TAG="Notify";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Test Notification");
        SharedPref=new SharedPref(this);
        misc=new Misc(this);
        misc.saveCurrentToken();




    }



}
