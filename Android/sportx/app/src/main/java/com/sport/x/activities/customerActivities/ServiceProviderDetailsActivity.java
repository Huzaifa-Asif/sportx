package com.sport.x.activities.customerActivities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.TooltipCompat;

import android.util.Log;
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
import com.sport.x.Models.BookingSettings;
import com.sport.x.Models.Expense;
import com.sport.x.activities.sharedActivities.MessageActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.activities.menu.Menu;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceProviderDetailsActivity extends Menu implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private Marker myMarker;
    private TextView name,email, service, phone, address,amount,opening,closing,duration,wholeDayAllowed,wholeDayPrice;
    private Location currentLocation;
    private double service_provider_longitude, service_provider_latitude;
    private String phoneNumber, service_name, service_provider_email,service_provider_name, service_provider_address, service_provider_phone_number;
    private int booking_setting_amount,booking_setting_duration,booking_setting_wholeDayBookingPrice;
    private String booking_setting_openingTime,booking_setting_closingTime;
    private boolean booking_setting_wholeDayBookingAllowed;
    private Button book, msg, call;
    private ImageButton compare,directions;
    private EditText meesage;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST_CODE = 101;
    SharedPref sharedPref;
    Misc misc;
    private boolean show = false;
    private String calling;

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
        amount=findViewById(R.id.amount);
        opening=findViewById(R.id.opening_time);
        closing=findViewById(R.id.closing_time);
        duration=findViewById(R.id.duration);
        wholeDayAllowed=findViewById(R.id.whole_day_booking_allowed);
        wholeDayPrice=findViewById(R.id.whole_day_booking_price);
        wholeDayAllowed.setVisibility(View.GONE);
        wholeDayPrice.setVisibility(View.GONE);
        directions=findViewById(R.id.directions);
        directions.setOnClickListener(this);

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
        calling=intent.getStringExtra("calling");
        callBookingSettingWebService();
//        booking_setting_amount=intent.getIntExtra("booking_setting_amount",500);
//        booking_setting_openingTime=intent.getStringExtra("booking_setting_openingTime");
//        booking_setting_closingTime=intent.getStringExtra("booking_setting_closingTime");
//        booking_setting_duration=intent.getIntExtra("booking_setting_duration",60);
//        booking_setting_wholeDayBookingAllowed=intent.getBooleanExtra("booking_setting_wholeDayBookingAllowed",false);
//        booking_setting_wholeDayBookingPrice=intent.getIntExtra("booking_setting_wholeDayBookingPrice",0);

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

            if(calling.equals("maps"))
            {
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("service_name",service_name);
                startActivity(intent);
                finish();
            }
            else if (calling.equals("searchByName"))
            {
                Intent intent = new Intent(this, SearchByNameActivity.class);
                startActivity(intent);
                finish();
            }



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
        else if(v.getId() == directions.getId()){

            getDirections(service_provider_latitude,service_provider_longitude);
        }
    }


    private void slecetForComparison()
    {
        String serviceProvider1=sharedPref.getCompareServiceProvider1(),serviceProvider2=sharedPref.getCompareServiceProvider2();
        String serviceProvider3=sharedPref.getCompareServiceProvider3(),serviceProvider4=sharedPref.getCompareServiceProvider4();
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
        else if(serviceProvider3==null)
        {

            sharedPref.setCompareServiceProvider3(service_provider_email);
            misc.showToast("Service Provider "+service_provider_name+" added for comparison");
        }
        else if(serviceProvider4==null)
        {

            sharedPref.setCompareServiceProvider4(service_provider_email);
            misc.showToast("Service Provider "+service_provider_name+" added for comparison");
        }
        else
        {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.compare_dialog);
            Button btnServiceProvider1=dialog.findViewById(R.id.serviceProvider1);
            Button btnServiceProvider2=dialog.findViewById(R.id.serviceProvider2);
            Button btnServiceProvider3=dialog.findViewById(R.id.serviceProvider3);
            Button btnServiceProvider4=dialog.findViewById(R.id.serviceProvider4);
            btnServiceProvider1.setText(serviceProvider1);
            btnServiceProvider2.setText(serviceProvider2);
            btnServiceProvider3.setText(serviceProvider3);
            btnServiceProvider4.setText(serviceProvider4);

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
            btnServiceProvider3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sharedPref.setCompareServiceProvider3(service_provider_email);
                    misc.showToast("Service Provider "+service_provider_name+" added for comparison");
                    dialog.dismiss();

                }
            });
            btnServiceProvider4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sharedPref.setCompareServiceProvider4(service_provider_email);
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

        intent.putExtra("booking_setting_openingTime", booking_setting_openingTime);
        intent.putExtra("booking_setting_closingTime", booking_setting_closingTime);
        intent.putExtra("booking_setting_amount", booking_setting_amount);
        intent.putExtra("booking_setting_duration", booking_setting_duration);
        intent.putExtra("booking_setting_wholeDayBookingAllowed", booking_setting_wholeDayBookingAllowed);
        intent.putExtra("booking_setting_wholeDayBookingPrice", booking_setting_wholeDayBookingPrice);

        startActivity(intent);
    }
    public void callBookingSettingWebService()
    {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching Service provider Details");
        pd.setCancelable(false);
        pd.show();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "bookingSetting/get_bookingSetting_by_serviceProvider/" + service_provider_email)
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

                        try {


                            JSONObject bookingSetting  = new JSONObject(result.getResult());
                            String bookingSettingId=bookingSetting.getString("_id");
                            booking_setting_amount=bookingSetting.getInt("amount");
                            booking_setting_openingTime=bookingSetting.getString("openingTime");
                            booking_setting_closingTime=bookingSetting.getString("closingTime");
                            booking_setting_duration=bookingSetting.getInt("duration");
                            int totalGrounds=bookingSetting.getInt("totalGrounds");
                            String spEmail=bookingSetting.getString("serviceProviderEmail");
                            booking_setting_wholeDayBookingAllowed=bookingSetting.getBoolean("wholeDayBookingAllowed");
                            booking_setting_wholeDayBookingPrice=0;
                            if(booking_setting_wholeDayBookingAllowed)
                            {
                                booking_setting_wholeDayBookingPrice=bookingSetting.getInt("wholeDayBookingPrice");
                            }

//                           BookingSettings bookingSettings=new BookingSettings(bookingSettingId,amount,openingTime,closingTime,duration,totalGrounds,spEmail,wholeDayBookingAllowed,wholeDayBookingPrice);


                            opening.setText("Opening Time: "+booking_setting_openingTime);
                            closing.setText("Closing Time: "+booking_setting_closingTime);
                            duration.setText("Booking Duration: "+booking_setting_duration+" minutes");
                            amount.setText("Booking Price: "+booking_setting_amount);
                            if(booking_setting_wholeDayBookingAllowed)
                            {
                                wholeDayAllowed.setText("Whole Day Booking Allowed: Yes");
                                wholeDayPrice.setText("Whole Day Booking Price: "+ booking_setting_wholeDayBookingPrice);
                            }
                            else
                            {
                                wholeDayAllowed.setText("Whole Day Booking Allowed: No");
                                wholeDayPrice.setVisibility(View.GONE);
                            }


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }

                });
        pd.dismiss();
    }

    private void getDirections(Double deslatitude,Double deslongitude){
        Double srclatitude=33.6207751;
        Double srclongitude=73.1011514;
        String geoUri = "http://maps.google.com/maps?saddr" + srclatitude + "," + srclongitude + "&daddr="+ deslatitude + "," + deslongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        startActivity(intent);

    }

}