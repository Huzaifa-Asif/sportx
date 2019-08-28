package com.sport.x.ServiceProviderActivities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.CreateTournamentActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.ConversationMessage;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.widget.AdapterView.OnItemSelectedListener;


public class AddRevenueActivity extends AppCompatActivity implements OnItemSelectedListener {
    String email="khuz@gmail.com";
    Context context;
    SharedPref SharedPref;
    Misc misc;
    Spinner spin;
    ArrayList<String> categories = new ArrayList<String>();
    EditText newCategory,amount,date,description,txtDate;
    Button add;
    String revenueCategory;
    private int mYear, mMonth, mDay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_revenue);
        setTitle("Add revenue");
        context = this;
        SharedPref = new SharedPref(context);
        misc = new Misc(context);
        newCategory=findViewById(R.id.newCategory);
        amount=findViewById(R.id.amount);
        txtDate=findViewById(R.id.in_date1);
        description=findViewById(R.id.description);
        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callAddrevenueWebService();
            }
        });

        newCategory.setVisibility(View.INVISIBLE);
        categories.add("Select Category");
        callrevenueCategoryWebservice(true);
        spin = findViewById(R.id.spinner);
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, categories) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spin.setAdapter(spinnerArrayAdapter);
        spin.setOnItemSelectedListener(this);

    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).toString().equals("Other"))
        {
            newCategory.setVisibility(View.VISIBLE);
        }
        else
        {
            revenueCategory=parent.getItemAtPosition(position).toString();
        }

    }



    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }





    public void callrevenueCategoryWebservice(boolean isShowDialog)
    {

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "get_revenueCategory_by_serviceProvider/" + email)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null) {

                            misc.showToast("Please check your connection");

                            return;
                        }

                        try {

                            JSONArray jsonArray = new JSONArray(result.getResult());

                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonObjectrevenueCategory = (JSONObject) jsonArray.get(i);

                                String categoryName = jsonObjectrevenueCategory.getString("name");
                                categories.add(categoryName);
                            }
                            categories.add("Other");


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });
    }

    private void callAddrevenueWebService(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Adding revenue...");
        pd.setCancelable(false);
        pd.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("revenueCategory", revenueCategory);
        jsonObject.addProperty("serviceProviderEmail", SharedPref.getEmail());
        jsonObject.addProperty("amount", amount.getText().toString());
        jsonObject.addProperty("date", txtDate.getText().toString());
        jsonObject.addProperty("description",description.getText().toString());


        Ion.with(this)
                .load(misc.ROOT_PATH+"add_revenue")
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
                                misc.showToast("revenue Added");
                                Intent intent = new Intent(AddRevenueActivity.this, RevenueActivity.class);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RevenueActivity.class);
        startActivity(intent);
        finish();
    }

}
