package com.sport.x.activities.sharedActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sport.x.activities.customerActivities.BookingManagement;
import com.sport.x.Misc.Misc;
import com.sport.x.activities.menu.Menu;
import com.sport.x.activities.serviceProviderActivities.BookingManagementActivity;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;
import static android.view.View.GONE;

public class PendingBookingDetailsActivity extends Menu implements View.OnClickListener {

    private GoogleMap mMap;
    private Marker myMarker;
    private double current_latitude, current_longitude;
    private TextView name, booking_type, phone, state, date, time;
    private Location currentLocation;
    private String phoneNumber, jobId;
    private Button accept, cancel, msg, call;
    private EditText meesage;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST_CODE = 101;
    SharedPref sharedPref;
    Misc misc;
    private boolean show = false;
    private String jobStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sh_pending_booking_details);
        setTitle("Pending Request");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);

        name = findViewById(R.id.c_name);
        booking_type = findViewById(R.id.c_booking_type);
        phone = findViewById(R.id.c_phone);
        state = findViewById(R.id.c_state);
        date = findViewById(R.id.c_date);
        time = findViewById(R.id.c_time);

        call = findViewById(R.id.make_call);
        call.setOnClickListener(this);

        msg = findViewById(R.id.message);
        msg.setOnClickListener(this);

        accept = findViewById(R.id.accept_job);
        accept.setOnClickListener(this);

        cancel = findViewById(R.id.cancel_job);
        cancel.setOnClickListener(this);

        Intent intent = getIntent();
        jobId = intent.getStringExtra("job_id");

        String id = sharedPref.getUserId();
        if(id == null) {
            accept.setVisibility(GONE);
        }

        int userRole = sharedPref.getUserRole();
        if(userRole == 1) {
            phoneNumber = intent.getStringExtra("customerNumber");

            name.setText("Name: " + intent.getStringExtra("customerName"));
            booking_type.setText("Booking Type: " + intent.getStringExtra("bookingType"));
            phone.setText("Phone: " + intent.getStringExtra("customerNumber"));
            state.setText("State: " + intent.getStringExtra("state"));
            date.setText("Date: " + intent.getStringExtra("date"));
            time.setText("Time: " + intent.getStringExtra("time"));

        }
        if(userRole == 2) {

            accept.setVisibility(GONE);
            cancel.setVisibility(GONE);

            phoneNumber = intent.getStringExtra("serviceProviderNumber");

            name.setText("Name: " + intent.getStringExtra("serviceProviderName"));
            booking_type.setText("Booking Type: " + intent.getStringExtra("bookingType"));
            phone.setText("Phone: " + intent.getStringExtra("serviceProviderNumber"));
            state.setText("State: " + intent.getStringExtra("state"));
            date.setText("Date: " + intent.getStringExtra("date"));
            time.setText("Time: " + intent.getStringExtra("time"));

        }



    }


    @Override
    public void onBackPressed() {

        if(sharedPref.getUserRole() == 1) {
            Intent intent = new Intent(this, BookingManagementActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, BookingManagement.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == accept.getId()) {
            acceptJob("accepted");
        }
        if(v.getId() == cancel.getId()) {
            cancelJob("canceled");
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
        intent.setData(Uri.parse("tel:"+ phoneNumber));
        startActivity(intent);
    }

    private void sendSMS(){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("address", phoneNumber);
        sendIntent.putExtra("sms_body", "Hi there!");
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }

    private void acceptJob(String status){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Accepting Booking Request... ");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", status);

        Ion.with(this)
                .load("PATCH",misc.ROOT_PATH+"bookingdetails/update_bookingState/"+jobId)
                .setJsonObjectBody(jsonObject)
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
                            try {
                                JSONObject jsonObject = new JSONObject(result.getResult());
                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                onBackPressed();
                                misc.showToast("Booking Request Accepted");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }


    private void cancelJob(String status){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Cancelling Booking Request... ");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", status);

        Ion.with(this)
                .load("PATCH",misc.ROOT_PATH+"bookingdetails/update_bookingState/"+jobId)
                .setJsonObjectBody(jsonObject)
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
                            try {
                                JSONObject jsonObject = new JSONObject(result.getResult());
                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                onBackPressed();
                                misc.showToast("Booking Request Canceled");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }

}
