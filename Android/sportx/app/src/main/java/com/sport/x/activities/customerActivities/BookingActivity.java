package com.sport.x.activities.customerActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sport.x.Adapters.BookingSlotAdapter;
import com.sport.x.Misc.Misc;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Models.BookingSlot;
import com.sport.x.Models.Job;
import com.sport.x.activities.menu.Menu;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

import org.json.JSONException;

// Date and Time
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BookingActivity extends Menu implements  View.OnClickListener {


    private Button register;
    Misc misc;
    SharedPref sharedPref;
    private String customer_email, customer_name, customer_number;


    // Intent get and set variables
    private double service_provider_longitude, service_provider_latitude;
    private String service_name, service_provider_email,service_provider_name, service_provider_address, service_provider_phone_number;


    private int booking_setting_amount,booking_setting_duration,booking_setting_wholeDayBookingPrice;
    private String booking_setting_openingTime,booking_setting_closingTime;
    private boolean booking_setting_wholeDayBookingAllowed;

    // Date and Time
    ImageButton btnDatePicker;
    EditText txtDate;
    private int mYear, mMonth, mDay, mHour, mMinute;


    //RecyclerView
    private ArrayList<BookingSlot> mModelList=new ArrayList<>();
    private ArrayList<Job> bookingsModel=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BookingSlotAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_c_booking);
        setTitle("Service Provider Booking");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);
        customer_email = sharedPref.getEmail();
        customer_name = sharedPref.getName();
        customer_number = sharedPref.getContact();

        // Date and Time

        btnDatePicker=findViewById(R.id.btn_date);
        txtDate=(EditText)findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(this);

        // Get Service Provider Profile Intent data

        Intent intent = getIntent();

        service_name=intent.getStringExtra("service_name");
        service_provider_email=intent.getStringExtra("service_provider_email");
        service_provider_name=intent.getStringExtra("service_provider_name");
        service_provider_address=intent.getStringExtra("service_provider_address");
        service_provider_phone_number = intent.getStringExtra("service_provider_phone_number");
        service_provider_latitude=intent.getDoubleExtra("service_provider_latitude",33);
        service_provider_longitude=intent.getDoubleExtra("service_provider_longitude",73);


        booking_setting_amount=intent.getIntExtra("booking_setting_amount",500);
        booking_setting_openingTime=intent.getStringExtra("booking_setting_openingTime");
        booking_setting_closingTime=intent.getStringExtra("booking_setting_closingTime");
        booking_setting_duration=intent.getIntExtra("booking_setting_duration",60);
        booking_setting_wholeDayBookingAllowed=intent.getBooleanExtra("booking_setting_wholeDayBookingAllowed",false);
        booking_setting_wholeDayBookingPrice=intent.getIntExtra("booking_setting_wholeDayBookingPrice",0);


        register = findViewById(R.id.register_button);
        register.setVisibility(View.GONE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(misc.isConnectedToInternet()){
                    getSelected();
                }
            }
        });

        //Adapter and Recycler View Link
        mRecyclerView =  findViewById(R.id.recycler_view_booking_slots);
        mAdapter = new BookingSlotAdapter(this,mModelList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager((new GridLayoutManager(this, 3)));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);

    }


    private void getSelected()
    {
        for (BookingSlot model : mModelList) {
            if (model.isSelected()) {
                Log.wtf("Selected Booking time is ",model.getStart());
                addBooking(model.getStart());
            }
        }

    }

    private void displayTimeSlots(){
        String openingTime = booking_setting_openingTime;
        StringTokenizer openingTokenizer = new StringTokenizer(openingTime,":");
        String openinghours = openingTokenizer.nextElement().toString();
        String openingminutes = openingTokenizer.nextElement().toString();
        openinghours = String.valueOf(Integer.parseInt(openinghours));
        if (Integer.parseInt(openingminutes) > 30){
            openingminutes = "00";
        }else{
            openingminutes = "30";
        }

        String closingTime = booking_setting_closingTime;
        StringTokenizer closingTokenizer = new StringTokenizer(closingTime,":");
        String closinghours = closingTokenizer.nextElement().toString();
        String closingminutes = closingTokenizer.nextElement().toString();
        closinghours = String.valueOf(Integer.parseInt(closinghours));
        if (Integer.parseInt(closingminutes) > 30){
            closingminutes = "00";
        }else {
            closingminutes = "30";
        }
        int totalHours=Integer.parseInt(closinghours)-Integer.parseInt(openinghours);
        int totalMinutes=0;
        if(openingminutes.equals(closingminutes))
        {
            totalMinutes=0;
        }
        else
        {
            totalMinutes=30;
        }
        int totalTime=(totalHours*60)+totalMinutes;

        Log.wtf("opening time ",openingTime);
        Log.wtf("opening hours ",openinghours);
        Log.wtf("opening minutes ",openingminutes);
        Log.wtf("closing time ",closingTime);
        Log.wtf("closing hours ",closinghours);
        Log.wtf("closing minutes ",closingminutes);

        Log.wtf("total Hours ",""+totalHours);
        Log.wtf("total Minutes ",""+totalMinutes);
        Log.wtf("total Time ",""+totalTime);

        int totalSlots=totalTime/booking_setting_duration;

        Log.wtf("total Slots ",""+totalSlots);
        String [] slotOpening=new String[totalSlots];
        String [] slotClosing=new String[totalSlots];
        for(int i=0;i<totalSlots;i++)
        {
            if(i==0)
            {
                slotOpening[i]=openingTime;
            }
            else
            {
                slotOpening[i]=slotClosing[i-1];
            }
            slotClosing[i]=(Integer.parseInt(openinghours)+(booking_setting_duration/60)*(i+1))+":"+openingminutes;
            mModelList.add(new BookingSlot(slotOpening[i],slotClosing[i],true,false));
        }


        for(int i=0;i<totalSlots;i++)
        {
            Log.wtf("Slot "+i,slotOpening[i]+" to "+ slotClosing[i]);

        }
        checkAvailability();

    }

    @Override
    public void onClick(View v) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            String dayString,monthString,yearString;
                            if(dayOfMonth<10)
                            {
                                dayString="0"+dayOfMonth;
                            }
                            else
                            {
                                dayString=""+dayOfMonth;
                            }
                            if(monthOfYear+1<10)
                            {
                                monthString="0"+(monthOfYear+1);
                            }
                            else
                            {
                                monthString=""+(monthOfYear+1);
                            }
                            yearString=""+year;
                            txtDate.setText(yearString + "-" +monthString + "-" + dayString);
                            mModelList.clear();
                            bookingsModel.clear();
                            register.setVisibility(View.VISIBLE);
                            callBookingDetailsWebService(txtDate.getText().toString());

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    private boolean validate(){


        return true;
    }





    private void addBooking(String time){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Booking in process...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        String type="";
        if(booking_setting_duration==60)
        {
            type="1 hour";
        }
        else if(booking_setting_duration==120)
        {
            type="2 hour";
        }
        else if(booking_setting_duration==180)
        {
            type="3 hour";
        }
        else if(booking_setting_duration==240)
        {
            type="4 hour";
        }
        jsonObject.addProperty("bookingType", type);
        jsonObject.addProperty("date", txtDate.getText().toString());
        jsonObject.addProperty("time", time);
        jsonObject.addProperty("serviceProviderEmail", service_provider_email);
        jsonObject.addProperty("serviceProviderName", service_provider_name);
        jsonObject.addProperty("serviceProviderNumber", service_provider_phone_number);
        jsonObject.addProperty("customerEmail", customer_email);
        jsonObject.addProperty("customerName", customer_name);
        jsonObject.addProperty("customerNumber", customer_number);
        jsonObject.addProperty("price", booking_setting_amount);


        Ion.with(this)
                .load(misc.ROOT_PATH+"bookingdetails/add_bookingDetails")
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
                                Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }



                    }
                });
        pd.dismiss();
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ServiceProviderDetailsActivity.class);
        intent.putExtra("service_provider_name",service_provider_name);
        intent.putExtra("service_name", service_name);
        intent.putExtra("service_provider_email", service_provider_email);
        intent.putExtra("service_provider_phone_number", service_provider_phone_number);
        intent.putExtra("service_provider_address", service_provider_address);
        intent.putExtra("service_provider_latitude", service_provider_latitude);
        intent.putExtra("service_provider_longitude", service_provider_longitude);
        intent.putExtra("calling","maps");
        startActivity(intent);

    }

    public void checkAvailability()
    {
        for(int i=0;i<bookingsModel.size();i++)
        {
            for(int j=0;j<mModelList.size();j++)
            {
                if((mModelList.get(j).getStart()).equals(bookingsModel.get(i).getTime()))
                {
                    mModelList.get(j).setAvailable(false);
                }
            }
        }
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
    }

    public void callBookingDetailsWebService(String selectedDate)
    {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching Availability for Selected Date");
        pd.setCancelable(false);
        pd.show();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "bookingdetails/get_bookingdetails_by_date?date="+selectedDate+"&email="+service_provider_email)
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


                            JSONArray jsonArray = new JSONArray(result.getResult());
                            if(jsonArray.length() < 1) {
                                misc.showToast("No Bookings found");
                                pd.dismiss();
                                displayTimeSlots();
                                return;
                            }
                            bookingsModel.clear();
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                String date = jsonObject.getString("date");
                                String job_id = jsonObject.getString("_id");
                                String bookingType = jsonObject.getString("bookingType");
                                String state = jsonObject.getString("state");
                                String time = jsonObject.getString("time");
                                String serviceProviderEmail = jsonObject.getString("serviceProviderEmail");
                                String serviceProviderName = jsonObject.getString("serviceProviderName");
                                String serviceProviderNumber = jsonObject.getString("serviceProviderNumber");
                                String customerEmail = jsonObject.getString("customerEmail");
                                String customerName = jsonObject.getString("customerName");
                                String customerNumber = jsonObject.getString("customerNumber");

                                bookingsModel.add(new Job(job_id, date, state, bookingType, time, serviceProviderEmail, serviceProviderName, serviceProviderNumber, customerEmail, customerName, customerNumber));
                            }

                            displayTimeSlots();
                            pd.dismiss();


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }

                });
        pd.dismiss();
    }



}




