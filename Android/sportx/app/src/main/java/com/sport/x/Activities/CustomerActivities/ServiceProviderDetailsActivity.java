package com.sport.x.Activities.CustomerActivities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.TooltipCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Activities.SharedActivites.MessageActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.Activities.Menu.Menu;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceProviderDetailsActivity extends Menu implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private Marker myMarker;
    private TextView name,email, service, phone, address;
    private Location currentLocation;
    private double service_provider_longitude, service_provider_latitude;
    private String phoneNumber, service_name, service_provider_email,service_provider_name, service_provider_address, service_provider_phone_number;
    private Button book, msg, call;
    private ImageButton compare;
    private EditText meesage;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST_CODE = 101;
    SharedPref sharedPref;
    Misc misc;
    private boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_c_service_provider_details);
        setTitle("Service Provider Profile");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);


        name = findViewById(R.id.c_name);
        email = findViewById(R.id.c_email);
        service = findViewById(R.id.c_service);
        phone = findViewById(R.id.c_phone);
        address= findViewById(R.id.c_address);
        call = findViewById(R.id.make_call);
        msg = findViewById(R.id.message);
        msg.setOnClickListener(this);

        book = findViewById(R.id.book);
        book.setOnClickListener(this);

        call.setOnClickListener(this);

        compare=findViewById(R.id.comparebutton);
        compare.setOnClickListener(this);
        TooltipCompat.setTooltipText(compare, "Select For Comparison");
        Intent intent = getIntent();

        service_name=intent.getStringExtra("service_name");
        service_provider_email=intent.getStringExtra("service_provider_email");
        service_provider_name=intent.getStringExtra("service_provider_name");
        service_provider_address=intent.getStringExtra("service_provider_address");
        service_provider_phone_number = intent.getStringExtra("service_provider_phone_number");
        service_provider_latitude=intent.getDoubleExtra("service_provider_latitude",33);
        service_provider_longitude=intent.getDoubleExtra("service_provider_longitude",73);

        name.setText("Name: " + service_provider_name);
        email.setText("Email: " + service_provider_email);
        service.setText("Service: " + service_name);
        phone.setText("Phone: " + service_provider_phone_number);
        address.setText("Address: " + service_provider_address);





        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.ip_map);
        mapFragment.getMapAsync(ServiceProviderDetailsActivity.this);


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
        LatLng sydney = new LatLng(service_provider_latitude, service_provider_longitude);
        mMap.setMinZoomPreference(15);
        myMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Service Provider Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onBackPressed() {

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("service_name",service_name);
            startActivity(intent);
            finish();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == book.getId()) {
            Book();
        }
        else if(v.getId() == call.getId()){
            makeCall();
        }
        else if(v.getId() == msg.getId()){
            sendSMS();
        }
        else if(v.getId() == compare.getId()){

            slecetForComparison();
        }
    }


    private void slecetForComparison()
    {
        String serviceProvider1=sharedPref.getCompareServiceProvider1(),serviceProvider2=sharedPref.getCompareServiceProvider2();

        if(serviceProvider1==null)
        {

            sharedPref.setCompareServiceProvider1(service_provider_email);
            misc.showToast("Service Provider "+service_provider_name+" added for comparison");
        }
        else if(serviceProvider2==null)
        {

            sharedPref.setCompareServiceProvider2(service_provider_email);
            misc.showToast("Service Provider "+service_provider_name+" added for comparison");
        }
        else
        {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.compare_dialog);
            Button btnServiceProvider1=dialog.findViewById(R.id.serviceProvider1);
            Button btnServiceProvider2=dialog.findViewById(R.id.serviceProvider2);
            btnServiceProvider1.setText(serviceProvider1);
            btnServiceProvider2.setText(serviceProvider2);
            btnServiceProvider1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sharedPref.setCompareServiceProvider1(service_provider_email);
                    misc.showToast("Service Provider "+service_provider_name+" added for comparison");
                    dialog.dismiss();

                }
            });
            btnServiceProvider2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sharedPref.setCompareServiceProvider2(service_provider_email);
                    misc.showToast("Service Provider "+service_provider_name+" added for comparison");
                    dialog.dismiss();

                }
            });
            dialog.show();

        }
    }

    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + service_provider_phone_number));
        startActivity(intent);
    }

    private void sendSMS(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Opening Chat...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("date",  new SimpleDateFormat("E,dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        jsonObject.addProperty("time",  new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
        jsonObject.addProperty("serviceProviderEmail", service_provider_email);
        jsonObject.addProperty("customerEmail", sharedPref.getEmail());


        Ion.with(this)
                .load(misc.ROOT_PATH+"conversation/add_conversation")
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
                            JSONObject jsonObject1 = new JSONObject(result.getResult());

                            Boolean status = jsonObject1.getBoolean("status");
                            String id=jsonObject1.getString("_id");

                            if (!status) {
                                String Message = jsonObject1.getString("Message");
                                pd.dismiss();
                                misc.showToast(Message);
                                return;
                            }
                            else if (status) {
                                pd.dismiss();

                                Intent intent = new Intent(ServiceProviderDetailsActivity.this, MessageActivity.class);
                                intent.putExtra("conversationId",id);
                                startActivity(intent);
                                finish();
                            }

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }



                    }
                });
    }

    private void Book(){
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("service_provider_name",service_provider_name);
        intent.putExtra("service_name", service_name);
        intent.putExtra("service_provider_email", service_provider_email);
        intent.putExtra("service_provider_phone_number", service_provider_phone_number);
        intent.putExtra("service_provider_address", service_provider_address);
        intent.putExtra("service_provider_latitude", service_provider_latitude);
        intent.putExtra("service_provider_longitude", service_provider_longitude);
        startActivity(intent);
    }



}