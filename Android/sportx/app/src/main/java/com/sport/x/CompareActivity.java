package com.sport.x;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Misc.Misc;
import com.sport.x.ServiceProviderActivities.Menu;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompareActivity extends Menu {
TextView name1,name2,bookings1,bookings2,ratings1,ratings2,avg1,avg2,distance1,distance2;
Misc misc;
SharedPref sharedPref;
private Location currentLocation;
private FusedLocationProviderClient fusedLocationProviderClient;
private double current_latitude, current_longitude;
private static final int LOCATION_REQUEST_CODE = 101;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_compare);
        setTitle("Compare Service providers");
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        bookings1 = findViewById(R.id.bookings1);
        bookings2 = findViewById(R.id.bookings2);
        ratings1 = findViewById(R.id.ratings1);
        ratings2 = findViewById(R.id.ratings2);
        avg1 = findViewById(R.id.avg1);
        avg2 = findViewById(R.id.avg2);
        distance1 = findViewById(R.id.distance1);
        distance2 = findViewById(R.id.distance2);
        misc=new Misc(this);
        sharedPref=new SharedPref(this);
        context=this;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(CompareActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CompareActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }
        fetchLastLocation();

    }


    private void fetchLastLocation() {
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
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    current_latitude = currentLocation.getLatitude();
                    current_longitude = currentLocation.getLongitude();
                    String email1=sharedPref.getCompareServiceProvider1();
                    String email2=sharedPref.getCompareServiceProvider2();
                    Log.wtf("sp1",email1);
                    Log.wtf("sp1",email2);
                    if(email1==null || email2==null)
                    {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.compare_dialog_service_provider_short);
                        TextView serviceProvider1=dialog.findViewById(R.id.serviceProvider1);
                        TextView serviceProvider2=dialog.findViewById(R.id.serviceProvider2);
                        if(email1==null)
                        {
                            serviceProvider1.setText("NULL");
                        }
                        else
                        {
                            serviceProvider1.setText(email1);
                        }
                        if(email2==null)
                        {
                            serviceProvider2.setText("NULL");
                        }
                        else
                        {
                            serviceProvider2.setText(email2);
                        }

                        Button accept=dialog.findViewById(R.id.accept);
                        accept.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(context, AllServiceActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                        dialog.show();
                    }
                    else
                    {
                        compare(email1,email2);
                    }

                    //Toast.makeText(MapsActivity.this,currentLocation.getLatitude()+" "+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(CompareActivity.this,"No Location recorded",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void compare(String email1,String email2){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Comparison in Progress...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("lat",current_latitude);
        jsonObject1.addProperty("long",current_longitude);
        jsonObject.addProperty("email1", email1);
        jsonObject.addProperty("email2", email2);
        jsonObject.add("location",jsonObject1);



        Ion.with(this)
                .load(misc.ROOT_PATH+"compare")
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null) {
                            pd.dismiss();
                            misc.showToast("Please check your connection");
                            pd.dismiss();
                            return;
                        }


                        try{
                            pd.dismiss();
                            JSONArray jsonArray = new JSONArray(result.getResult());
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                            JSONObject jsonObject2 = (JSONObject) jsonArray.get(1);
                            name1.setText(jsonObject1.getString("name"));
                            name2.setText(jsonObject2.getString("name"));
                            bookings1.setText(jsonObject1.getString("totalBookings"));
                            bookings2.setText(jsonObject2.getString("totalBookings"));
                            ratings1.setText(jsonObject1.getString("totalRatings"));
                            ratings2.setText(jsonObject2.getString("totalRatings"));
                            avg1.setText(jsonObject1.getString("avgRating"));
                            avg2.setText(jsonObject2.getString("avgRating"));
                            distance1.setText(jsonObject1.getString("distance"));
                            distance2.setText(jsonObject2.getString("distance"));




                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();

                        }



                    }
                });
    }
    @Override
    public void onBackPressed() {

            Intent intent = new Intent(this, AllServiceActivity.class);
            startActivity(intent);
            finish();


    }
}
