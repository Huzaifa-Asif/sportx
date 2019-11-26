package com.sport.x.activities.serviceProviderActivities;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.activities.menu.Menu;
import com.sport.x.activities.sharedActivities.SplashActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class AddBookingSettingsActivity extends Menu implements  View.OnClickListener{

    private Button submit;
    private ImageButton btnStartTime,btnEndTime;
    private Spinner spinner;
    private EditText price,startTime,endTime,totalGrounds,dayBookingAmount;
    private CheckBox dayBookingAllowed;

    Misc misc;
    SharedPref sharedPref;
    private int mHour, mMinute,duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sp_add_booking_settings);
        setTitle("Service Provider Booking");
        misc=new Misc(this);
        sharedPref=new SharedPref(this);
        submit=findViewById(R.id.submit);
        btnStartTime=findViewById(R.id.btn_start_time);
        btnEndTime=findViewById(R.id.btn_end_time);
        spinner=findViewById(R.id.spinner1);
        price=findViewById(R.id.price);
        startTime=findViewById(R.id.startTime);
        endTime=findViewById(R.id.endTime);
        totalGrounds=findViewById(R.id.totalGrounds);
        dayBookingAmount=findViewById(R.id.dayBookingAmount);
        dayBookingAllowed=findViewById(R.id.dayBookingAllowed);
        dayBookingAmount.setVisibility(View.GONE);
        btnEndTime.setOnClickListener(this);
        btnStartTime.setOnClickListener(this);
        dayBookingAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayBookingAllowed.isChecked())
                {
                    dayBookingAmount.setVisibility(View.VISIBLE);
                }
                else
                {
                    dayBookingAmount.setVisibility(View.GONE);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validate()) {
                    callAddBookingSettingsWebService();
                }
            }
        });
        spinner.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spinner.getSelectedItem().toString().equalsIgnoreCase("1-hour"))
                {
                    duration=60;
                }
                else if(spinner.getSelectedItem().toString().equalsIgnoreCase("2-hour"))
                {
                    duration=120;
                }
                else if(spinner.getSelectedItem().toString().equalsIgnoreCase("3-hour"))
                {
                    duration=180;
                }
                else if(spinner.getSelectedItem().toString().equalsIgnoreCase("4-hour"))
                {
                    duration=240;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }




    @Override
    public void onClick(View v) {

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
                            String hour,minutes;
                            if(hourOfDay<10)
                            {
                                hour="0"+hourOfDay;
                            }
                            else
                            {
                                hour=""+hourOfDay;
                            }
                            if(minute<10)
                            {
                                minutes="0"+minute;
                            }
                            else
                            {
                                minutes=""+minute;
                            }
                            if(v == btnStartTime)
                            {
                                startTime.setText(hour + ":" + minutes);
                            }
                            else if(v == btnEndTime)
                            {
                                endTime.setText(hourOfDay + ":" + minute);
                            }

                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }



    private boolean validate(){





        return true;
    }


    private void callAddBookingSettingsWebService(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Adding Booking Settings...");
        pd.setCancelable(false);
        pd.show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("serviceProviderEmail", sharedPref.getEmail());
        jsonObject.addProperty("amount", Integer.parseInt(price.getText().toString()));
        jsonObject.addProperty("openingTime", startTime.getText().toString());
        jsonObject.addProperty("closingTime", endTime.getText().toString());
        jsonObject.addProperty("duration", duration);
        jsonObject.addProperty("totalGrounds", totalGrounds.getText().toString());
        if(dayBookingAllowed.isChecked())
        {
            jsonObject.addProperty("wholeDayBookingAllowed",true);
            jsonObject.addProperty("wholeDayBookingPrice",dayBookingAmount.getText().toString());
        }

        Ion.with(this)
                .load(misc.ROOT_PATH+"bookingsetting/add_bookingSetting")
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
                                misc.showToast("Booking Settings Added");
                                sharedPref.setProfileCompleted(true);
                                Intent intent = new Intent(AddBookingSettingsActivity.this, SplashActivity.class);
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


}
