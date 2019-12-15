package com.sportx.pk.activities.serviceProviderActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sportx.pk.activities.menu.Menu;
import com.sportx.pk.Adapters.RevenueAdapter;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.Revenue;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RevenueActivity extends Menu {


    Misc misc;
    SharedPref SharedPref;
    Context context;
    ArrayList<Revenue> revenues = new ArrayList<Revenue>();
    private RecyclerView revenueRecycler;
    private RevenueAdapter revenueAdapter;
    FloatingActionButton add;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sp_revenue);
        setTitle("Revenues");
        context = this;
        SharedPref = new SharedPref(context);
        misc = new Misc(context);
        callrevenueWebservice(true);
        add=findViewById(R.id.fab);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RevenueActivity.this, AddRevenueActivity.class);
                startActivity(intent);
                finish();
            }
        });

        revenueRecycler =  findViewById(R.id.reyclerview_revenue_list);
        revenueRecycler.addOnScrollListener(new RecyclerView.OnScrollListener(){
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
        revenueAdapter = new RevenueAdapter(this, revenues);
        revenueRecycler.setAdapter(revenueAdapter);
        revenueRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this)
        {

            @Override
            public boolean isAutoMeasureEnabled() {
                return false;
            }
        };

        revenueRecycler.setLayoutManager(llm);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountsActivity.class);
        startActivity(intent);
        finish();
    }
    public void callrevenueWebservice(boolean isShowDialog)
    {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all revenues");
        pd.setCancelable(false);
        if(revenues.size()==0) {
            pd.show();
        }
        final int revenueSize = revenues.size();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "revenue/get_revenue_by_serviceProvider/" + SharedPref.getEmail())
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
                                misc.showToast("No revenues Found");
                                pd.dismiss();
                                return;
                            }
                            pd.dismiss();
                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonObjectrevenue = (JSONObject) jsonArray.get(i);

                                String revenueId = jsonObjectrevenue.getString("_id");
                                String revenueCategory = jsonObjectrevenue.getString("revenueCategory");
                                int amount = jsonObjectrevenue.getInt("amount");
                                String serviceProviderEmail = jsonObjectrevenue.getString("serviceProviderEmail");
                                String date = jsonObjectrevenue.getString("date");
                                String description = jsonObjectrevenue.getString("description");
                                revenues.add(new Revenue(revenueId, revenueCategory, amount, serviceProviderEmail, date, description));

                            }

                            revenueAdapter.notifyDataSetChanged();


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });
    }






}
