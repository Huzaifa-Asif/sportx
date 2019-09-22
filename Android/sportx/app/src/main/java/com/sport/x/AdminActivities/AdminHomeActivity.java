package com.sport.x.AdminActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.sport.x.R;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView users, service_providers, complaints, add_new_service, job_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        setTitle("Admin Dashboard");

        users = findViewById(R.id.all_users);
        service_providers = findViewById(R.id.all_service_providers);
        complaints = findViewById(R.id.all_complaints);
        add_new_service = findViewById(R.id.add_servive);
        job_history = findViewById(R.id.all_jobs);

        users.setOnClickListener(this);
        service_providers.setOnClickListener(this);
        complaints.setOnClickListener(this);
        add_new_service.setOnClickListener(this);
        job_history.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == users.getId()){
            Intent intent = new Intent(this, AllCustomersActivity.class);
            startActivity(intent);
            finish();
        }
//        if(v.getId() == booking_management.getId()){
//            Intent intent = new Intent(this, AllJobsActivity.class);
//            startActivity(intent);
//            finish();
//        }
        if(v.getId() == service_providers.getId()){
            Intent intent = new Intent(this, AllServiceProvidersActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId() == complaints.getId()){
            Intent intent = new Intent(this, AllComplaintsActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId() == add_new_service.getId()){
            Intent intent = new Intent(this, AddServiceActivity.class);
            startActivity(intent);
            finish();
        }
    }
}