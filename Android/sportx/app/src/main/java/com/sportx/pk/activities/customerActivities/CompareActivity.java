package com.sportx.pk.activities.customerActivities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

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
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.activities.menu.Menu;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompareActivity extends Menu {
TextView name1,name2,name3,name4,bookings1,bookings2,bookings3,bookings4,ratings1,ratings2,ratings3,ratings4,avg1,avg2,avg3,avg4,distance1,distance2,distance3,distance4;
Misc misc;
SharedPref sharedPref;
private Location currentLocation;
private FusedLocationProviderClient fusedLocationProviderClient;
private double current_latitude, current_longitude;
private static final int LOCATION_REQUEST_CODE = 101;
    TextView names[] = new TextView[4];
    TextView bookings[] = new TextView[4];
    TextView ratings[] = new TextView[4];
    TextView avgs[] = new TextView[4];
    TextView distances[] = new TextView[4];
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_c_compare);
        setTitle("Compare Service providers");
        names[0] = findViewById(R.id.name1);
        names[1] = findViewById(R.id.name2);
        names[2] = findViewById(R.id.name3);
        names[3] = findViewById(R.id.name4);

        bookings[0] = findViewById(R.id.bookings1);
        bookings[1] = findViewById(R.id.bookings2);
        bookings[2] = findViewById(R.id.bookings3);
        bookings[3] = findViewById(R.id.bookings4);

        ratings[0] = findViewById(R.id.ratings1);
        ratings[1] = findViewById(R.id.ratings2);
        ratings[2] = findViewById(R.id.ratings3);
        ratings[3] = findViewById(R.id.ratings4);

        avgs[0] = findViewById(R.id.avg1);
        avgs[1] = findViewById(R.id.avg2);
        avgs[2] = findViewById(R.id.avg3);
        avgs[3] = findViewById(R.id.avg4);

        distances[0] = findViewById(R.id.distance1);
        distances[1] = findViewById(R.id.distance2);
        distances[2] = findViewById(R.id.distance3);
        distances[3] = findViewById(R.id.distance4);


        for(int i=0;i<names.length;i++)
        {
            names[i].setText("-");
            bookings[i].setText("-");
            ratings[i].setText("-");
            avgs[i].setText("-");
            distances[i].setText("-");
        }
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
                    String email3=sharedPref.getCompareServiceProvider3();
                    String email4=sharedPref.getCompareServiceProvider4();

                    compare(email1,email2,email3,email4);

                }else{
                    Toast.makeText(CompareActivity.this,"No Location recorded",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void compare(String email1,String email2,String email3,String email4){

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
        jsonObject.addProperty("email3", email3);
        jsonObject.addProperty("email4", email4);
        jsonObject.add("location",jsonObject1);



        Ion.with(this)
                .load(misc.ROOT_PATH+"serviceprovider/compare")
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
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject1=(JSONObject) jsonArray.get(i);
                                names[i].setText(jsonObject1.getString("name"));
                                bookings[i].setText(jsonObject1.getString("totalBookings"));
                                if(jsonObject1.getString("avgRating").equals("NaN"))
                                {
                                    avgs[i].setText("-");
                                }
                                else
                                {
                                    avgs[i].setText(jsonObject1.getString("avgRating"));

                                }
                                ratings[i].setText(jsonObject1.getString("totalRatings"));
                                distances[i].setText(jsonObject1.getString("distance"));

                            }





                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();

                        }



                    }
                });
    }
    @Override
    public void onBackPressed() {

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();


    }
}
