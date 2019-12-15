package com.sportx.pk.activities.serviceProviderActivities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.sportx.pk.activities.menu.Menu;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends Menu
        implements View.OnClickListener {

    private TextView pending_booking_text, in_progress_booking_text, completed_booking_text;
    private TextView pending_tournament_text, in_progress_tournament_text, completed_tournament_text;
    private CircularImageView img1;
    Misc misc;
    SharedPref sharedPref;
    private String password;
    private double latitude1, longitude1;
    private int pending_booking_sum=0, in_progress_booking_sum=0, completed_booking_sum=0;
    private int pending_tournament_sum=0, in_progress_tournament_sum=0, completed_tournament_sum=0;
    private CardView new_order, in_transit, completed_order;
    int i;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CALL_PHONE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sp_home);

        setTitle("Home");

        sharedPref = new SharedPref(this);
        misc = new Misc(this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        misc.saveCurrentToken();

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        // Booking Count Text View
        pending_booking_text = findViewById(R.id.pending_booking_text);
        in_progress_booking_text = findViewById(R.id.in_progress_booking_text);
        completed_booking_text = findViewById(R.id.completed_booking_text);

        // Tournament Count Text View
        pending_tournament_text = findViewById(R.id.pending_tournament_text);
        in_progress_tournament_text = findViewById(R.id.in_progress_tournament_text);
        completed_tournament_text = findViewById(R.id.completed_tournament_text);

        // Card Views
        new_order = findViewById(R.id.new_order);
        in_transit = findViewById(R.id.in_transit_order);
        completed_order = findViewById(R.id.completed_order);


        new_order.setOnClickListener(this);
        in_transit.setOnClickListener(this);
        completed_order.setOnClickListener(this);


        getBookingStats();
        getTournamentStats();


    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void getBookingStats(){

        Ion.with(this)
                .load(misc.ROOT_PATH+"bookingdetails/get_bookingdetails/"+ sharedPref.getEmail())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null){
                            misc.showToast("Please check your connection");
                            return;
                        }
                        else{
                            if(!result.getResult().isEmpty()){
                                try {
                                    JSONArray jsonArray = new JSONArray(result.getResult());
                                    if(jsonArray.length() < 1) {

                                    }
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                                        String state = jsonObject.getString("state");
                                        if(state.equals("pending")){
                                            pending_booking_sum++;
                                        }
                                        else if(state.equals("accepted")){
                                            in_progress_booking_sum++;
                                        }
                                        else if(state.equals("completed")){
                                            completed_booking_sum++;
                                        }
                                    }

                                    pending_booking_text.setText("Pending Bookings: " + pending_booking_sum);
                                    in_progress_booking_text.setText("In-Progress Bookings: " + in_progress_booking_sum);
                                    completed_booking_text.setText("Completed Bookings: " + completed_booking_sum);


                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }

                        }
                    }
                });
    }


    private void getTournamentStats(){

        Ion.with(this)
                .load(misc.ROOT_PATH+"tournament/get_tournament_by_serviceProvider/"+sharedPref.getEmail())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null){
                            misc.showToast("Please check your connection");
                            return;
                        }
                        else{
                            if(!result.getResult().isEmpty()){
                                try {
                                    JSONArray jsonArray = new JSONArray(result.getResult());
                                    if(jsonArray.length() < 1) {

                                    }
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                                        String state = jsonObject.getString("state");
                                        if(state.equals("inactive")){
                                            pending_tournament_sum++;
                                        }
                                        else if(state.equals("active")){
                                            in_progress_tournament_sum++;
                                        }
                                        else if(state.equals("completed")){
                                            completed_tournament_sum++;
                                        }
                                    }

                                    pending_tournament_text.setText("Pending Tournament: " + pending_tournament_sum);
                                    in_progress_tournament_text.setText("In-Progress Tournament: " + in_progress_tournament_sum);
                                    completed_tournament_text.setText("Completed Tournament: " + completed_tournament_sum);


                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }

                        }
                    }
                });
    }



    @Override
    public void onClick(View v) {
//        if(v.getId() == new_order.getId()){
//            Intent intent = new Intent(this, NewOrderActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else if(v.getId() == in_transit.getId()){
//            Intent intent = new Intent(this, InTransitOrderActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else if(v.getId() == completed_order.getId()){
//            Intent intent = new Intent(this, CompletedOrderActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




}
