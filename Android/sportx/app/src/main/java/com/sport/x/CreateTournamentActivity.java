package com.sport.x;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Misc.Misc;
import com.sport.x.Activities.ServiceProviderActivities.Menu;
import com.sport.x.Activities.ServiceProviderActivities.ServiceHomeActivity;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;


// Date and Time
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;


public class CreateTournamentActivity extends Menu implements OnItemSelectedListener,View.OnClickListener{

    private EditText name, no_of_days, entry_fee, winning_prize, txtDate, txtTime;

    private Button submit,btnDatePicker, btnTimePicker;

    Spinner tournamentType = null;

    Spinner teamNo = null;

    private int mYear, mMonth, mDay, mHour, mMinute;

    String adder_email, service_provider_email, tournament_type, no_of_teams, tournament_date,tournament_time, tournament_name, winningprize, entryfee, maxdays;

    Misc misc;

    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_create_tournament);
        setTitle("Create Tournament");

        misc = new Misc(this);

        sharedPref = new SharedPref(this);

        // as admin can add tournament directly so adder will also be service provider

        adder_email = sharedPref.getEmail();

        service_provider_email = sharedPref.getEmail();

        name=findViewById(R.id.tournament_name);

        no_of_days=findViewById(R.id.tournament_days);

        entry_fee=findViewById(R.id.tournament_entry_fee);

        winning_prize=findViewById(R.id.tournament_winning_prize);

        txtDate=findViewById(R.id.in_date);

        txtTime=findViewById(R.id.in_time);



        btnDatePicker = findViewById(R.id.btn_date);

        btnDatePicker.setOnClickListener(this);

        btnTimePicker = findViewById(R.id.btn_time);

        btnTimePicker.setOnClickListener(this);

        //Spinner
        tournamentType = findViewById(R.id.tournamentType);

        tournamentType.setOnItemSelectedListener(this);

        teamNo = findViewById(R.id.no_of_team);

        teamNo.setOnItemSelectedListener(this);




        submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!misc.isConnectedToInternet()){
                    misc.showToast("No Internet Connection");
                }
                else{
                    createTournament();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ServiceHomeActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void onClick(View v) {

        if(v.getId() == btnDatePicker.getId()) {
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
                            txtDate.setText(dayString + "-" +monthString + "-" + yearString);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if(v.getId() == btnTimePicker.getId()) {
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

    }




    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        switch(parent.getId()){
        if(parent.getId()==R.id.tournamentType)
        {
            tournament_type = parent.getItemAtPosition(position).toString();
        }
        else if(parent.getId()==R.id.no_of_team)
        {
            no_of_teams = parent.getItemAtPosition(position).toString();
        }
//                break;
//            case R.id.no_of_team :
//                no_of_teams= parent.getItemAtPosition(position).toString();
//                break;
        }



    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }



    private boolean validate(){

        tournament_name = name.getText().toString().trim();
        winningprize = winning_prize.getText().toString().trim();
        entryfee = entry_fee.getText().toString().trim();
        maxdays = no_of_days.getText().toString().trim();
        tournament_date= txtDate.getText().toString();
        tournament_time= txtTime.getText().toString();

        String regex = "[A-Za-z A-Za-z]+";
        String regex2 = "[a-zA-Z0-9._-]+";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if(tournament_name.length() < 3 && !tournament_name.matches(regex2)){
            misc.showToast("Invalid Tournament Name");
            name.setError("Invalid Tournament Name");
            return false;
        }

        if(tournament_type.equals("Select Tournament Type") ){
            misc.showToast("Kindly Select Tournament Type");
            return false;
        }

        if(no_of_teams.equals("No of Teams") ){
            misc.showToast("Kindly Select No of Teams");
            return false;
        }

        if(maxdays.length() < 1 ){
            misc.showToast("Kindly enter No of Days");
            return false;
        }

        if(entryfee.length() < 3 ){
            misc.showToast("Invalid Entry Fee");
            return false;
        }

        if(winningprize.length() < 3 ){
            misc.showToast("Invalid Winning Prize");
            return false;
        }








        if(tournament_date.length() < 7 ){
            misc.showToast("Invalid Date");
            //name.setError("Invalid Date");
            return false;
        }
        if(tournament_time.length() < 3 ){
            misc.showToast("Invalid Time");
            //name.setError("Invalid Time");
            return false;
        }



        return true;
    }




    private void createTournament()
    {
        if(validate())
        {
            tournament();
        }

    }

    private void tournament(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Creating Tournament...");
        pd.setCancelable(false);
        pd.show();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", tournament_name);
        jsonObject.addProperty("teams", no_of_teams);
        jsonObject.addProperty("winningPrize", winningprize);
        jsonObject.addProperty("entryFee", entryfee);
        jsonObject.addProperty("tournamentType", tournament_type);
        jsonObject.addProperty("maxDays", maxdays);
        jsonObject.addProperty("serviceProviderEmail", service_provider_email);
        jsonObject.addProperty("adderEmail", adder_email);
        jsonObject.addProperty("startDate", tournament_date);
        jsonObject.addProperty("startTime", tournament_time);
        jsonObject.addProperty("date",  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));


        Ion.with(this)
                .load(misc.ROOT_PATH+"tournament/add_tournament")
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
                            JSONObject jsonObject2 = new JSONObject(result.getResult());

                            Boolean status = jsonObject2.getBoolean("status");


                            if (!status) {
                                String Message = jsonObject2.getString("Message");
                                pd.dismiss();
                                misc.showToast(Message);
                                return;
                            }
                            else if (status) {

                                pd.dismiss();

                                misc.showToast("Tournament Created Successfully");
                                Intent intent = new Intent(CreateTournamentActivity.this, TournamentActivity.class);
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
