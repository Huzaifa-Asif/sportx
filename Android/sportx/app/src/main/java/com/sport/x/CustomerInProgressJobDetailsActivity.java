package com.sport.x;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sport.x.AdminActivities.AllJobsActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import static android.view.View.GONE;

public class CustomerInProgressJobDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private Marker myMarker;
    private double current_latitude, current_longitude;
    private TextView name, service, phone, city;
    private Location currentLocation;
    private String phoneNumber, jobId;
    private Button complete, msg, call;
    private EditText meesage;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST_CODE = 101;
    SharedPref sharedPref;
    Misc misc;
    private boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_in_progress_job_details);
        setTitle("Job In Progress");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);

        name = findViewById(R.id.c_name);
        service = findViewById(R.id.c_service);
        phone = findViewById(R.id.c_phone);
        city = findViewById(R.id.c_city);
        call = findViewById(R.id.make_call);
        msg = findViewById(R.id.message);

        msg.setOnClickListener(this);
        complete = findViewById(R.id.complete_job);
        complete.setOnClickListener(this);
        call.setOnClickListener(this);

        String id = sharedPref.getUserId();
        if(id == null) {
            complete.setVisibility(GONE);
        }

        Intent intent = getIntent();
        jobId = intent.getStringExtra("job_id");

        name.setText("Name: " + intent.getStringExtra("vendor_name"));
        service.setText("Service: " + intent.getStringExtra("service_name"));
        phone.setText("Phone: " + intent.getStringExtra("phone"));
        city.setText("City: " + intent.getStringExtra("city"));

        phoneNumber = intent.getStringExtra("phone");

        current_latitude = Double.parseDouble(intent.getStringExtra("lat"));
        current_longitude = Double.parseDouble(intent.getStringExtra("lon"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.ip_map);
        mapFragment.getMapAsync(CustomerInProgressJobDetailsActivity.this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(current_latitude, current_longitude);
        mMap.setMinZoomPreference(15);
        myMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Service Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onBackPressed() {
        if(sharedPref.getUserId() == null) {
            Intent intent = new Intent(this, AllJobsActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, JobHistoryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == complete.getId()) {
            completeJob();
        }
        if(v.getId() == call.getId()){
            makeCall();
        }
        if(v.getId() == msg.getId()){
            sendSMS();
        }
    }

    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void sendSMS(){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("address", phoneNumber);
        sendIntent.putExtra("sms_body", "Hi there!");
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }

    private void completeJob(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Completing Jobs... ");
        pd.setCancelable(false);
        pd.show();

        Ion.with(this)
                .load("PUT",misc.ROOT_PATH+"complete_job/"+jobId)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null) {
                            misc.showToast("Please check your connection");
                            pd.dismiss();
                            return;
                        }
                        else{
                            pd.dismiss();
                            misc.showToast(result.getResult());
                            onBackPressed();

                        }
                    }
                });
    }
}