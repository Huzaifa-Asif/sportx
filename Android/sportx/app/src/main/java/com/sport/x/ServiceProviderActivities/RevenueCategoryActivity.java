package com.sport.x.ServiceProviderActivities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Adapters.RevenueCategoryAdapter;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.RevenueCategory;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RevenueCategoryActivity extends Menu {
    Misc misc;
    SharedPref SharedPref;
    Context context;
    ArrayList<RevenueCategory> categories = new ArrayList<RevenueCategory>();
    private RecyclerView revenueCategoryRecycler;
    private RevenueCategoryAdapter revenueCategoryAdapter;
    FloatingActionButton add;
    EditText newCategory;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_revenue_category);
        setTitle("Revenue Category");
        context = this;
        SharedPref = new SharedPref(context);
        misc = new Misc(context);
        callRevenueCategoryWebservice(true);
        add=findViewById(R.id.fab);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_revenue_category);
                newCategory = dialog.findViewById(R.id.category);
                Button add=dialog.findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callAddRevenueCategoryWebservice();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });


        revenueCategoryRecycler =  findViewById(R.id.reyclerview_revenue_list);
        revenueCategoryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !add.isShown())
                    add.show();
                else if(dy>0 && add.isShown())
                    add.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        revenueCategoryAdapter = new RevenueCategoryAdapter(this, categories);
        revenueCategoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        revenueCategoryRecycler.setAdapter(revenueCategoryAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountsActivity.class);
        startActivity(intent);
        finish();
    }

    private void callAddRevenueCategoryWebservice(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Adding revenue Category...");
        pd.setCancelable(false);
        pd.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", newCategory.getText().toString());
        jsonObject.addProperty("serviceProviderEmail", SharedPref.getEmail());



        Ion.with(this)
                .load(misc.ROOT_PATH+"revenuecategory/add_revenueCategory")
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



                            }

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }



                    }
                });
    }



    public void callRevenueCategoryWebservice(boolean isShowDialog)
    {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all revenue categories");
        pd.setCancelable(false);
        if(categories.size()==0) {
            pd.show();
        }
        final int revenueCategorySize = categories.size();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "revenuecategory/get_revenueCategory_by_serviceProvider/" + SharedPref.getEmail())
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
                            if (jsonArray.length() < 1) {
                                misc.showToast("No revenue Categories Found");
                                pd.dismiss();
                                return;
                            }
                            pd.dismiss();
                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonObjectRevenueCategory = (JSONObject) jsonArray.get(i);

                                String revenueCategoryId = jsonObjectRevenueCategory.getString("_id");
                                String name = jsonObjectRevenueCategory.getString("name");
                                String serviceProviderEmail = jsonObjectRevenueCategory.getString("serviceProviderEmail");
                                categories.add(new RevenueCategory(revenueCategoryId, name, serviceProviderEmail));

                            }

                            revenueCategoryAdapter.notifyDataSetChanged();


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });
    }




}
