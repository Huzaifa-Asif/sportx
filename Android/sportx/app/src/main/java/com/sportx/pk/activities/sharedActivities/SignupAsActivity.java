package com.sportx.pk.activities.sharedActivities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.sportx.pk.R;

public class SignupAsActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView customer, service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_signup_as);
        setTitle("Register As");

        customer = findViewById(R.id.register_customer);
        service = findViewById(R.id.register_service);

        customer.setOnClickListener(this);
        service.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == customer.getId()){
            Intent intent = new Intent(this, com.sportx.pk.activities.customerActivities.SignupActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId() == service.getId()){
            Intent intent = new Intent(this, com.sportx.pk.activities.serviceProviderActivities.SignupActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
