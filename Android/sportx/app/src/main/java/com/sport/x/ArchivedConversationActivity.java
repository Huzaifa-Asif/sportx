package com.sport.x;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

//import com.dps.mydoctor.R;
//import com.dps.mydoctor.activities.BaseActivity;
//import com.dps.mydoctor.adapters.ConversationsAdapter;
//import com.dps.mydoctor.callbacks.OnItemClickListener;
//import com.dps.mydoctor.models.ConversationsModel;
//import com.dps.mydoctor.utils.ApiConstant;
//import com.dps.mydoctor.utils.RestApiCall;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class ArchivedConversationActivity {

    RecyclerView rv_conversations;
    ArrayList<ConversationsModel> conversationsModels=null;
    ConversationsAdapter conversationsAdapter=null;
    SwipeRefreshLayout srf_conversations=null;
    @Override
    public int myView() {
        return R.layout.activity_conversations;
    }

    @Override
    public void activityCreated() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                ArchiveConversationsActivity.this.finish();
            }
        });
        // getSupportActionBar().setTitle("Conversations");
        myVdoctorApp.setToolBarTitle(toolbar,"Archive Conversations");
        rv_conversations=findViewById(R.id.rv_conversations);
        srf_conversations=findViewById(R.id.srf_conversations);
        rv_conversations.setLayoutManager(new LinearLayoutManager(this));
        conversationsModels=new ArrayList<>();
       /*conversationsModels.add(new ConversationsModel("","Test Patient",""));
        conversationsModels.add(new ConversationsModel("","Test Patient",""));
        conversationsModels.add(new ConversationsModel("","Test Patient",""));
        conversationsModels.add(new ConversationsModel("","Test Patient",""));*/

        conversationsAdapter=new ConversationsAdapter(this,myVdoctorApp,appPreference.getType(), conversationsModels, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String user_name="";
                if(appPreference.getType().equals("1")){
                    user_name=conversationsModels.get(position).getPatient_full_name();
                }else{
                    user_name=conversationsModels.get(position).getDoctor_full_name();
                }
                Intent intent=new Intent(getApplication(),ArchiveMessagesActivity.class);
                intent.putExtra("conversation_id",conversationsModels.get(position).getConversation_id());
                intent.putExtra("user_name",user_name);
                if(appPreference.getType().equals("1")) {
                    intent.putExtra("user_id", conversationsModels.get(position).getPatient_user_id());
                    intent.putExtra("user_profile_pic", conversationsModels.get(position).getPatient_profile_picture());
                }else{
                    intent.putExtra("user_id", conversationsModels.get(position).getDoctor_user_id());
                    intent.putExtra("user_profile_pic", conversationsModels.get(position).getDoctor_profile_picture());
                }
                intent.putExtra("patient_id",conversationsModels.get(position).getPatient_id());
                startActivity(intent);
            }
        });
        rv_conversations.setAdapter(conversationsAdapter);
        callConversationListWebservice();
        srf_conversations.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callConversationListWebservice();
            }
        });

    }
    public void callConversationListWebservice() {
        String name="";
        if(appPreference.getType().equals("1")){
            name="doctor_id";
        }else{
            name="patient_id";
        }
        RequestBody requestBody = new FormBody.Builder()
                .add(name, appPreference.getDoctorId())
                .build();

        new RestApiCall(this, true, ApiConstant.CONVERSATIONS_LIST+"?"+name+"="+appPreference.getDoctorId()+"&status='Archived'" , "get", false, requestBody, new RestApiCall.AsyncResponse() {

            @Override
            public void processFinish(String response) {
                if (response != null) {
                    Log.e("Response2", "Response Registration:" + response);
                    try {
                        JSONObject jsonParent = (JSONObject) new JSONTokener(response).nextValue();
                        conversationsModels.clear();
                        String status=jsonParent.getString("status");
                        if(status.equals("success")) {
                            JSONArray jsonDataArray = (JSONArray) jsonParent.getJSONArray("data");
                            for (int i = 0; i < jsonDataArray.length(); i++) {
                                JSONObject jsonObjectConversation = jsonDataArray.getJSONObject(i);
                                JSONObject jsonObjectRequest = jsonObjectConversation.getJSONObject("request");

                                JSONObject jsonObjectPatient = jsonObjectConversation.getJSONObject("patient");
                                JSONObject jsonObjectDoctor = jsonObjectConversation.getJSONObject("doctor");

                                conversationsModels.add(new ConversationsModel(
                                        jsonObjectConversation.getString("conversation_id"),
                                        jsonObjectRequest.getString("request_id"), jsonObjectRequest.getString("request_text"), jsonObjectRequest.getString("request_time"),
                                        jsonObjectPatient.getString("user_id"), jsonObjectPatient.getString("patient_id"),
                                        jsonObjectPatient.getString("profile_picture"), jsonObjectPatient.getString("first_name"),
                                        jsonObjectPatient.getString("last_name"), jsonObjectPatient.getString("full_name"),
                                        jsonObjectDoctor.getString("user_id"), jsonObjectDoctor.getString("doctor_id"),
                                        jsonObjectDoctor.getString("profile_picture"), jsonObjectDoctor.getString("first_name"),
                                        jsonObjectDoctor.getString("last_name"), jsonObjectDoctor.getString("full_name")));


                            }
                            conversationsAdapter.notifyDataSetChanged();
                        }else{
                            myVdoctorApp.showToast(context,jsonParent.getString("msg"));
                        }
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        myVdoctorApp.showToast(context, getResources().getString(R.string.unable_to_contact_server_please_try_again_later));
                    }
                }
                if(srf_conversations.isRefreshing()){
                    srf_conversations.setRefreshing(false);
                }
            }

        }).execute();

    }
}
