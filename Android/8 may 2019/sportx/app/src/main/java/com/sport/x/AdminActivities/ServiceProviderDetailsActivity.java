package com.sport.x.AdminActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sport.x.R;

public class ServiceProviderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_details);
        setTitle("Muhammad Ali");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RegisteredServiceProvidersActivity.class);
        startActivity(intent);
        finish();
    }
}
