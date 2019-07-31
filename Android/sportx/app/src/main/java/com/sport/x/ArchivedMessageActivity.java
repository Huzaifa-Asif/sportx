package com.sport.x;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

//import com.dps.mydoctor.R;
//import com.dps.mydoctor.activities.BaseActivity;
//import com.dps.mydoctor.activities.doctorActivities.PatientProfileActivity;
//import com.dps.mydoctor.adapters.MessageAdapter;
//import com.dps.mydoctor.callbacks.OnItemClickListener;
//import com.dps.mydoctor.models.MessagesModel;
//import com.dps.mydoctor.utils.ApiConstant;
//import com.dps.mydoctor.utils.RestApiCall;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class ArchivedMessageActivity {

    RecyclerView rv_messages;
    String conversation_id;
    String user_name, patient_id, user_id, user_profile_pic;
    ArrayList<MessagesModel> messagesModels = null;
    MessageAdapter messageAdapter = null;


    @Override
    public int myView() {
        return R.layout.activity_messages;
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
                ArchiveMessagesActivity.this.finish();
            }
        });
        findViewById(R.id.footer).setAlpha((float) .3);
        findViewById(R.id.edt_message).setClickable(false);
        findViewById(R.id.edt_message).setFocusable(false);
        findViewById(R.id.edt_message).setFocusableInTouchMode(false);
        findViewById(R.id.edt_message).setLongClickable(false);
        findViewById(R.id.btn_send).setClickable(false);
        findViewById(R.id.btn_send).setFocusable(false);
        findViewById(R.id.btn_send).setFocusableInTouchMode(false);
        if (getIntent() != null) {
            conversation_id = getIntent().getStringExtra("conversation_id");
            user_name = getIntent().getStringExtra("user_name");
            user_id = getIntent().getStringExtra("user_id");
            patient_id = getIntent().getStringExtra("patient_id");
            user_profile_pic = getIntent().getStringExtra("user_profile_pic");
        }

        //     getSupportActionBar().setTitle(user_name);
        myVdoctorApp.setToolBarTitle(toolbar, user_name);
        rv_messages = findViewById(R.id.rv_messages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_messages.setLayoutManager(linearLayoutManager);
        messagesModels = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, myVdoctorApp, appPreference.getUserId(), messagesModels, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        rv_messages.setAdapter(messageAdapter);
        callConversationMessagesWebservice(true);


    }


    public void callConversationMessagesWebservice(boolean isShowDialog) {

        RequestBody requestBody = new FormBody.Builder()
                .build();

        new RestApiCall(this, isShowDialog, ApiConstant.CONVERSATIONS_MESSAGES + "?conversation_id=" + conversation_id, "get", false, requestBody, new RestApiCall.AsyncResponse() {

            @Override
            public void processFinish(String response) {
                if (response != null) {
                    Log.e("Response2", "Response Registration:" + response);
                    try {
                        JSONObject jsonParent = (JSONObject) new JSONTokener(response).nextValue();
                        //  messagesModels.clear();
                        String status = jsonParent.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonDataArray = (JSONArray) jsonParent.getJSONArray("data");

                            for (int i = 0; i < jsonDataArray.length(); i++) {
                                JSONObject jsonObjectConversation = jsonDataArray.getJSONObject(i);
                                JSONObject jsonObjectPatient = jsonObjectConversation.getJSONObject("patient");
                                JSONObject jsonObjectDoctor = jsonObjectConversation.getJSONObject("doctor");
                                JSONObject jsonObjectMessage = jsonObjectConversation.getJSONObject("message");
                                messagesModels.add(new MessagesModel(
                                        jsonObjectConversation.getString("conversation_id"),
                                        jsonObjectPatient.getString("patient_id"),
                                        jsonObjectPatient.getString("profile_picture"), jsonObjectPatient.getString("first_name"),
                                        jsonObjectPatient.getString("last_name"), jsonObjectPatient.getString("full_name"),
                                        jsonObjectDoctor.getString("doctor_id"),
                                        jsonObjectDoctor.getString("profile_picture"), jsonObjectDoctor.getString("first_name"),
                                        jsonObjectDoctor.getString("last_name"), jsonObjectDoctor.getString("full_name"),
                                        jsonObjectMessage.getString("message"), jsonObjectMessage.getString("sender"),
                                        jsonObjectMessage.getString("message_type"), jsonObjectMessage.getString("attachment"),
                                        jsonObjectMessage.getString("time")));

                            }
                            messageAdapter.notifyDataSetChanged();
                            //   rv_messages.smoothScrollToPosition(messagesModels.size() - 1);

                        } else {

                            myVdoctorApp.showToast(context, jsonParent.getString("msg"));

                        }
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        myVdoctorApp.showToast(context, getResources().getString(R.string.unable_to_contact_server_please_try_again_later));
                    }
                }

            }

        }).execute();

    }

    public void openGallary(View view) {
    }
}
