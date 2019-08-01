package com.sport.x;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

//import com.dps.mydoctor.R;
//import com.dps.mydoctor.activities.BaseActivity;
//import com.dps.mydoctor.adapters.ConversationsAdapter;
//import com.dps.mydoctor.callbacks.OnItemClickListener;
//import com.dps.mydoctor.models.ConversationsModel;
//import com.dps.mydoctor.utils.ApiConstant;
//import com.dps.mydoctor.utils.RestApiCall;

import com.sport.x.AdminActivities.AllJobsActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Conversation;
import com.sport.x.Adapters.ConversationsAdapter;
import com.sport.x.ServiceProviderActivities.ProviderJobsActivity;
import com.sport.x.ServiceProviderActivities.ServiceHomeActivity;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ConversationActivity extends AppCompatActivity {

    RecyclerView rv_conversations;
    ArrayList<Conversation> conversationModel=null;
    ConversationsAdapter conversationsAdapter=null;
    SwipeRefreshLayout srf_conversations=null;

    Misc misc;
    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        setTitle("Conversations");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);


    }


    @Override
    public void activityCreated() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //What to do on back clicked
//                ConversationsActivity.this.finish();
//            }
//        });
        // getSupportActionBar().setTitle("Conversations");
//        myVdoctorApp.setToolBarTitle(toolbar,"Conversations");
        rv_conversations=findViewById(R.id.rv_conversations);
        srf_conversations=findViewById(R.id.srf_conversations);
        rv_conversations.setLayoutManager(new LinearLayoutManager(this));
        conversationModel=new ArrayList<>();
       /*conversationsModels.add(new ConversationsModel("","Test Patient",""));
        conversationsModels.add(new ConversationsModel("","Test Patient",""));
        conversationsModels.add(new ConversationsModel("","Test Patient",""));
        conversationsModels.add(new ConversationsModel("","Test Patient",""));*/

        conversationsAdapter=new ConversationsAdapter(this,sharedPref.getUserRole(), conversationModel, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(appPreference.getType().equals("1")){
                    if(conversationsModels.get(position).getPaid()){
                        startNextActivity(position);
                    }else{
                        myVdoctorApp.showToast(context,"Sorry,Only Patient can initiate chat.");
                    }
                }else{
                    if(conversationsModels.get(position).getPaid()){
                        startNextActivity(position);
                    }else{
                        showDialogWithCancel(position,"This Will Charge you: "+conversationsModels.get(position).getMessage_fee()+"  Rupees from your Wallet.",context);
                    }
                }

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



    // On back press method
    @Override
    public void onBackPressed() {

        if(sharedPref.getUserRole() == 1) {
            Intent intent = new Intent(this, ServiceHomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, AllServiceActivity.class);
            startActivity(intent);
            finish();
        }
    }



    public void startNextActivity(int position){
        String user_email="";
        if(sharedPref.getUserRole()==2){
            user_email=conversationModel.get(position).getConversationCustomerEmail();
        }else{
            user_email=conversationModel.get(position).getConversationServiceProviderEmail();
        }
        Intent intent=new Intent(getApplication(),MessageActivity.class);
        intent.putExtra("conversationId",conversationModel.get(position).getConversation_id());
        intent.putExtra("customer_email",user_name);
        intent.putExtra("customer_email",user_name);
        intent.putExtra("customer_email",user_name);

        intent.putExtra("index",position);
        intent.putExtra("fee",conversationsModels.get(position).getCall_fee());
        if(appPreference.getType().equals("1")) {
            intent.putExtra("user_id", conversationsModels.get(position).getPatient_user_id());
            intent.putExtra("user_profile_pic", conversationsModels.get(position).getPatient_profile_picture());
        }else{
            intent.putExtra("user_id", conversationsModels.get(position).getDoctor_user_id());
            intent.putExtra("user_profile_pic", conversationsModels.get(position).getDoctor_profile_picture());
        }
        intent.putExtra("patient_id",conversationsModels.get(position).getPatient_id());
        startActivityForResult(intent,505);
    }


    public void showDialogWithCancel(int position,String msg, final Context context)
    {

        final AlertDialog dialog = new AlertDialog.Builder(context).setMessage(msg).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conversationsModels.get(position).setPaid("Done");
                        startNextActivity(position);

                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        if (dialog.isShowing()) {
            dialog.dismiss();

        } else {
            dialog.show();
        }

        dialog.setCancelable(true);
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

        new RestApiCall(this, true, ApiConstant.CONVERSATIONS_LIST+"?"+name+"="+appPreference.getDoctorId()+"&status=\"Active\"", "get", false, requestBody, new RestApiCall.AsyncResponse() {

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
                                ConversationsModel conversationsModel=new ConversationsModel(
                                        jsonObjectConversation.getString("conversation_id"),
                                        jsonObjectRequest.getString("request_id"), jsonObjectRequest.getString("request_text"), jsonObjectRequest.getString("request_time"),
                                        jsonObjectPatient.getString("user_id"), jsonObjectPatient.getString("patient_id"),
                                        jsonObjectPatient.getString("profile_picture"), jsonObjectPatient.getString("first_name"),
                                        jsonObjectPatient.getString("last_name"), jsonObjectPatient.getString("full_name"),
                                        jsonObjectDoctor.getString("user_id"), jsonObjectDoctor.getString("doctor_id"),
                                        jsonObjectDoctor.getString("profile_picture"), jsonObjectDoctor.getString("first_name"),
                                        jsonObjectDoctor.getString("last_name"), jsonObjectDoctor.getString("full_name"));
                                conversationsModel.setMessage_fee(jsonObjectDoctor.getString("doctor_message_fee"));
                                conversationsModel.setCall_fee(jsonObjectDoctor.getString("doctor_call_fee"));
                                conversationsModel.setPaid(jsonObjectRequest.getString("payment"));
                                conversationsModels.add(conversationsModel);

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

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==505&&resultCode == Activity.RESULT_OK){
            Log.e("index",data.getIntExtra("index",-1)+"");
            if(data.getIntExtra("index",-1)!=-1){
                try {
                    conversationsModels.remove(data.getIntExtra("index", -1));
                    conversationsAdapter.notifyDataSetChanged();
                }catch (IndexOutOfBoundsException e){

                }
            }
        }
    }

}
