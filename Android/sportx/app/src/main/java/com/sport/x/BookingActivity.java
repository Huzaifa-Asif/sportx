package com.sport.x;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.sport.x.Misc.Misc;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

// Date and Time
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;


public class BookingActivity extends AppCompatActivity implements OnItemSelectedListener {

    private EditText name, email, phone, password, re_password;
    private RadioButton male, female;
    private Button register;
    private CircleImageView image;
    private String bitmapTo64, selectedGender = null;
    private static String resultPath = null;
    private final int REQUEST_CODE = 1;
    private File uploadFile = null;
    Misc misc;
    SharedPref sharedPref;
    private String customer_email, customer_name, customer_number;
    String imagebase64;

    private String  booking_date, booking_time, booking_type;

    Spinner bookingType = null;;

    // Intent get and set variables
    private double service_provider_longitude, service_provider_latitude;
    private String service_name, service_provider_email,service_provider_name, service_provider_address, service_provider_phone_number;

    // Date and Time

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setTitle("Service Provider Booking");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);
        customer_email = sharedPref.getEmail();
        customer_name = sharedPref.getName();
        customer_number = sharedPref.getNumber();

        name = findViewById(R.id.full_name);
        phone = findViewById(R.id.reg_phone);
        email = findViewById(R.id.register_email);


        // Date and Time

        //btnDatePicker=(Button)findViewById(R.id.btn_date);
        //btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

//        btnDatePicker.setOnClickListener(this);
//        btnTimePicker.setOnClickListener(this);

        // Get Service Provider Profile Intent data

        Intent intent = getIntent();

        service_name=intent.getStringExtra("service_name");
        service_provider_email=intent.getStringExtra("service_provider_email");
        service_provider_name=intent.getStringExtra("service_provider_name");
        service_provider_address=intent.getStringExtra("service_provider_address");
        service_provider_phone_number = intent.getStringExtra("service_provider_phone_number");
        service_provider_latitude=intent.getDoubleExtra("service_provider_latitude",33);
        service_provider_longitude=intent.getDoubleExtra("service_provider_longitude",73);



        //Spinner
        bookingType = (Spinner)findViewById(R.id.spinner1);
        bookingType.setOnItemSelectedListener(this);

        register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(misc.isConnectedToInternet()){
                    registerBooking();
                }
            }
        });




    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        booking_type= parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), booking_type, Toast.LENGTH_SHORT).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }



    private void registerBooking()
    {

        if(validate())
        {
            addBooking();

        }
    }

//

//    @Override
//    public void onClick(View v)
//    {

//
//        if (v == btnDatePicker) {

            protected  void DatePickerClick(View v){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }


            protected void TimePickerClick(View v){
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }


    private boolean validate(){

        booking_date= txtDate.getText().toString();
        booking_time= txtTime.getText().toString();


        if(booking_date.length() < 7 ){
            misc.showToast("Invalid Date");
            //name.setError("Invalid Date");
            return false;
        }
        if(booking_time.length() < 3 ){
            misc.showToast("Invalid Time");
            //name.setError("Invalid Time");
            return false;
        }


        return true;
    }





    private void addBooking(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Booking in process...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("bookingType", booking_type);
        jsonObject.addProperty("date", booking_date);
        jsonObject.addProperty("time", booking_time);
        jsonObject.addProperty("serviceProviderEmail", service_provider_email);
        jsonObject.addProperty("serviceProviderName", service_provider_name);
        jsonObject.addProperty("serviceProviderNumber", service_provider_phone_number);
        jsonObject.addProperty("customerEmail", customer_email);
        jsonObject.addProperty("customerName", customer_name);
        jsonObject.addProperty("customerNumber", customer_number);


        Ion.with(this)
                .load(misc.ROOT_PATH+"add_bookingDetails")
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


                            if (!status) {
                                String Message = jsonObject1.getString("Message");
                                pd.dismiss();
                                misc.showToast(Message);
                                return;
                            }
                            else if (status) {
                                pd.dismiss();
                                misc.showToast("Booking Request Sent, Now wait for approval");
                                Intent intent = new Intent(BookingActivity.this, AllServiceActivity.class);
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



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, service_provider_profile_Activity.class);

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




