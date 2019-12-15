package com.sportx.pk.activities.sharedActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sportx.pk.Adapters.StreamAdapter;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.LiveStream;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;
import com.sportx.pk.activities.customerActivities.HomeActivity;
import com.sportx.pk.activities.menu.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OngoingStreamsActivity extends Menu {
    SharedPref sharedPref;
    Misc misc;
    ArrayList<LiveStream> streams = new ArrayList<LiveStream>();
    private RecyclerView streamsRecycler;
    private StreamAdapter streamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sh_ongoing_streams);
        sharedPref=new SharedPref(this);
        misc=new Misc(this);
        getOngoingStreams();
        streamsRecycler=findViewById(R.id.reyclerview_streams_list);
        streamAdapter=new StreamAdapter(this,streams);
        streamsRecycler.setAdapter(streamAdapter);
//        streamsRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this)
        {

            @Override
            public boolean isAutoMeasureEnabled() {
                return false;
            }
        };

        streamsRecycler.setLayoutManager(llm);
    }


    public void getOngoingStreams()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all Ongoing Streams");
        pd.setCancelable(false);
        if(streams.size()==0) {
            pd.show();
        }

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "livestream/ongoingstreams")
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
                                misc.showToast("No Ongoing Streams Available");
                                pd.dismiss();
                                return;
                            }
                            pd.dismiss();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObjectStream = (JSONObject) jsonArray.get(i);

                                String id = jsonObjectStream.getString("_id");
                                String email = jsonObjectStream.getString("serviceProviderEmail");
                                String name = jsonObjectStream.getString("serviceProviderName");
                                String picture = jsonObjectStream.getString("serviceProvider_picture_profile");
                                String date = jsonObjectStream.getString("date");
                                String time = jsonObjectStream.getString("time");
                                Boolean ongoing = jsonObjectStream.getBoolean("ongoing");
                                streams.add(new LiveStream(id,email,name,picture,date,time,ongoing));


                            }

                            streamAdapter.notifyDataSetChanged();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
