package com.sport.x.Activities.ServiceProviderActivities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.sport.x.Activities.Menu.Menu;
import com.sport.x.Misc.Misc;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import android.widget.AdapterView.OnItemSelectedListener;


public class AddRevenueActivity extends Menu implements OnItemSelectedListener,View.OnClickListener {
    String email;
    Context context;
    SharedPref SharedPref;
    Misc misc;
    Spinner spin;
    ArrayList<String> categories = new ArrayList<String>();
    EditText newCategory,amount,description,txtDate;
    Button add,btndate;
    String revenueCategory;
    private int mYear, mMonth, mDay;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sp_add_revenue);
        setTitle("Add revenue");
        context = this;
        SharedPref = new SharedPref(context);
        misc = new Misc(context);
        email=SharedPref.getEmail();
        newCategory=findViewById(R.id.newCategory);
        amount=findViewById(R.id.amount);
        txtDate=findViewById(R.id.in_date1);
        description=findViewById(R.id.description);
        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validate())
                {
                    callAddrevenueWebService();
                }

            }
        });
        btndate=findViewById(R.id.btn_date);
        btndate.setOnClickListener(this);
        newCategory.setVisibility(View.GONE);
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
                        txtDate.setText(dayString + "-" +monthString + "-" + yearString);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).toString().equals("Other"))
        {
            newCategory.setVisibility(View.VISIBLE);

        }
        else
        {
            revenueCategory=parent.getItemAtPosition(position).toString();
            newCategory.setVisibility(View.GONE);
        }

    }



    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }



    private boolean validate(){

        String amount1 = amount.getText().toString().trim();
        String date=txtDate.getText().toString();
        String description1= description.getText().toString();
        String newCategory1=newCategory.getText().toString();
        if(date.length() < 7 ){
            misc.showToast("Invalid Date");
            return false;
        }
        else if(description1.isEmpty())
        {
            misc.showToast("Kindly Enter description");
            return false;
        }
        else if(amount1.isEmpty())
        {
            misc.showToast("Kindly Enter Amount");
            return false;
        }
        else if(spin.getSelectedItem().toString().equals("Select Category"))
        {
            misc.showToast("Kindly Select Category");
            return false;
        }
        else if(spin.getSelectedItem().toString().equals("Other")&& newCategory1.isEmpty())
        {
            misc.showToast("Kindly Enter New Category");
            return false;
        }



        return true;
    }

    public void callrevenueCategoryWebservice(boolean isShowDialog)
    {

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "revenueCategory/get_revenueCategory_by_serviceProvider/" + email)
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
        if(spin.getSelectedItem().toString().equals("Other"))
        {
            jsonObject.addProperty("revenueCategory", newCategory.getText().toString());
        }
        else
        {
            jsonObject.addProperty("revenueCategory", revenueCategory);
        }


        jsonObject.addProperty("serviceProviderEmail", SharedPref.getEmail());
        jsonObject.addProperty("amount", amount.getText().toString());
        jsonObject.addProperty("date", txtDate.getText().toString());
        jsonObject.addProperty("description",description.getText().toString());


        Ion.with(this)
                .load(misc.ROOT_PATH+"revenue/add_revenue")
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



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RevenueActivity.class);
        startActivity(intent);
        finish();
    }

}
