package com.sport.x;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import com.dps.mydoctor.MyVdoctorApp;
//import com.dps.mydoctor.R;
//import com.dps.mydoctor.activities.sharedActivities.FullScreenImageActivity;
//import com.dps.mydoctor.callbacks.OnItemClickListener;
//import com.dps.mydoctor.models.ConversationsModel;
//import com.dps.mydoctor.models.MessagesModel;
//import com.dps.mydoctor.utils.ApiConstant;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.sport.x.Adapters.ConversationMessagesAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.sport.x.Models.Service_Provider;
import com.sport.x.SharedPref.SharedPref;


import com.sport.x.Models.ConversationMessage;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;


import android.content.Context;

import com.sport.x.Misc.Misc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MessageActivity extends AppCompatActivity {

    RecyclerView rv_messages;
    int index;
    String conversationId;
    String conversationMessageId, senderEmail, message, type,file_path,date;
    ArrayList<ConversationMessage> messageModel = null;
    ConversationMessagesAdapter messageAdapter = null;
    EditText edt_message;
    Button btn_send;/*
    CountDownTimer counter = null;*/
    SharedPref SharedPref;
    File file = null;
    int REQUEST_GET_SINGLE_FILE = 101;
    int REQUEST_GET_CAMERA_FILE = 100;
    int count = 0;

    private Context context;

    Misc misc;

    Handler handler = new Handler();
    Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            callConversationMessagesWebservice(false);
            handler.postDelayed(this, 3000);
        }
    };




    public int myView() {
        return R.layout.activity_messages;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    public void activityCreated() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                MessageActivity.this.finish();
            }
        });
        if (getIntent() != null) {
            index=getIntent().getIntExtra("index",-1);
            Log.e("index",index+"");
            conversationId = getIntent().getStringExtra("conversationId");
            conversationMessageId = getIntent().getStringExtra("conversationMessageId");
            senderEmail = getIntent().getStringExtra("senderEmail");
            message = getIntent().getStringExtra("message");
            type = getIntent().getStringExtra("type");
            file_path = getIntent().getStringExtra("file_path");
            date = getIntent().getStringExtra("date");
        }

        //     getSupportActionBar().setTitle(user_name);
        //myVdoctorApp.setToolBarTitle(toolbar, senderEmail);
        rv_messages = findViewById(R.id.rv_messages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_messages.setLayoutManager(linearLayoutManager);
       /* messagesModels=new ArrayList<>();
        messagesModels.add(new MessagesModel("","Test Patient"));
        messagesModels.add(new MessagesModel("","Test Patient"));
        messagesModels.add(new MessagesModel("","Test Patient"));
        messagesModels.add(new MessagesModel("","Test Patient"));
*/
        messageModel = new ArrayList<>();

        messageAdapter = new ConversationMessagesAdapter(this, SharedPref.getUserRole(), messageModel, new SharedPref.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });


        rv_messages.setAdapter(messageAdapter);
        callConversationMessagesWebservice(true);

        edt_message = findViewById(R.id.edt_message);

        btn_send = findViewById(R.id.btn_send);
        disableSendButton();
        edt_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                if (count > 0) {
                    enableSendButton();
                } else {
                    disableSendButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        if (handler != null) {
            handler.removeCallbacks(mUpdateTimeTask);
            handler.postDelayed(mUpdateTimeTask, 3000);
        }

    }

    public void enableSendButton() {
        btn_send.setClickable(true);
        btn_send.setAlpha(1);
    }

    public void disableSendButton() {
        btn_send.setClickable(false);
        btn_send.setAlpha((float) 0.7);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (SharedPref.getUserRole()==1) {
            getMenuInflater().inflate(R.menu.activity_service_home_drawer, menu);
        } else {
            getMenuInflater().inflate(R.menu.activity_all_service_drawer, menu);
            // return false;
        }
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
////            case R.id.action_phoneCall:
////                // User chose the "Settings" item, show the app settings UI...
////                if (myVdoctorApp.isOnline(context)) {
////                    Log.e("receiverId", user_id);
////                    callCheckWalletWebservice();
////                } else {
////                    myVdoctorApp.showToast(context, "No internet connection,Please try again later.");
////                }
////                return true;
////
////            case R.id.action_profile:
////                // User chose the "Favorite" action, mark the current item
////                // as a favorite...
////
////                Intent intent = new Intent(new Intent(this, PatientProfileActivity.class));
////                intent.putExtra("patient_id", patient_id);
////                startActivity(intent);
////                return true;
//
//            case R.id.action_close:
//                callChatArchivedWebservice();
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
/*
    int i = 0;*/

    /*public void startTimer() {
        if (!isTimerRunning) {
            counter = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilDone) {

                    isTimerRunning = true;
                    Log.e("second",millisUntilDone+"");
                }

                public void onFinish() {
                    //   Log.d("Service Hit after ", i + " second");
                    isTimerRunning = false;
                    callConversationMessagesWebservice(false);
                    startTimer();
                    Log.e("interval", i + "");
                    i++;
                }
            }.start();
        }




    }*/


    public void callConversationMessagesWebservice(boolean isShowDialog) {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all messages");
        pd.setCancelable(false);
        pd.show();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH+"get_message_by_conversationId/"+conversationId)
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
                            JSONArray jsonArray = new JSONArray(result.getResult());
                            if(jsonArray.length() < 1) {
                                misc.showToast("No Messages Found");
                                pd.dismiss();
                                return;
                            }
                            vendorList.clear();
                            for(int i = 1; i < jsonArray.length(); i++){

//                                    misc.showToast("For Loop"+result.getResult());

                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                JSONObject location  = jsonObject.getJSONObject("location");

                                double latitude1 = location.getDouble("lat");
                                double longitude1 = location.getDouble("long");

                                String service_provider_name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                String address = jsonObject.getString("address");
                                String picture_profile = jsonObject.getString("picture_profile");
                                String contact = jsonObject.getString("contact");
                                String password = jsonObject.getString("password");
                                String category = jsonObject.getString("category");

//
                                //vendorList.add(new Service_Provider(service_provider_name, email, address, picture, contact, password, category, latitude1, longitude1));

                                if(getDistance(latitude1, longitude1) < 25) {
                                    vendorList.add(new Service_Provider(service_provider_name, email, address, picture_profile, contact, password, category, latitude1, longitude1));
                                }
                            }
                            if(vendorList.isEmpty()){
                                misc.showToast("No Nearby " + service_name + " Found. Please visit again");
                                pd.dismiss();
                                return;
                            }

                            for(m = 0; m<vendorList.size();m++)
                            {
                                LatLng serviceLocation = new LatLng(vendorList.get(m).getUserLat(), vendorList.get(m).getUserLon());
                                myMarker = mMap.addMarker(new MarkerOptions().position(serviceLocation).title(vendorList.get(m).getServiceProviderName()).alpha(m));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(serviceLocation));
                            }
//                                LatLng serviceLocation = new LatLng(vendorList.get(0).getUserLat(), vendorList.get(0).getUserLon());
//                                mMap.setMinZoomPreference(15);
//                                myMarker = mMap.addMarker(new MarkerOptions().position(serviceLocation).title(vendorList.get(0).getServiceProviderName()));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLng(serviceLocation));
                            mMap.setMinZoomPreference(10);
                            pd.dismiss();

                            // double distance = getDistanceFromLatLonInKm(current_latitude, current_longitude, service_lat, service_lon);
                            //  misc.showToast(String.valueOf(getDistance(Double.parseDouble(vendorList.get(0).getUserLat()), Double.parseDouble(vendorList.get(0).getUserLon()))));
                            //fetchRating(vendorList.get(0).getUserId());
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
    });


//        RequestBody requestBody = new FormBody.Builder()
//                .build();
//
//        new RestApiCall(this, isShowDialog, ApiConstant.CONVERSATIONS_MESSAGES + "?conversation_id=" + conversation_id, "get", false, requestBody, new RestApiCall.AsyncResponse() {
//
//            @Override
//            public void processFinish(String response) {
//                if (response != null) {
//                    Log.e("Response2", "Response Registration:" + response);
//                    try {
//                        JSONObject jsonParent = (JSONObject) new JSONTokener(response).nextValue();
//                        //  messagesModels.clear();
//                        int messagesSize = messagesModels.size();
//                        String status = jsonParent.getString("status");
//                        if (status.equals("success")) {
//                            JSONArray jsonDataArray = (JSONArray) jsonParent.getJSONArray("data");
//
//                            for (int i = messagesSize; i < jsonDataArray.length(); i++) {
//                                JSONObject jsonObjectConversation = jsonDataArray.getJSONObject(i);
//                                JSONObject jsonObjectPatient = jsonObjectConversation.getJSONObject("patient");
//                                JSONObject jsonObjectDoctor = jsonObjectConversation.getJSONObject("doctor");
//                                JSONObject jsonObjectMessage = jsonObjectConversation.getJSONObject("message");
//                                messagesModels.add(new MessagesModel(
//                                        jsonObjectConversation.getString("conversation_id"),
//                                        jsonObjectPatient.getString("patient_id"),
//                                        jsonObjectPatient.getString("profile_picture"), jsonObjectPatient.getString("first_name"),
//                                        jsonObjectPatient.getString("last_name"), jsonObjectPatient.getString("full_name"),
//                                        jsonObjectDoctor.getString("doctor_id"),
//                                        jsonObjectDoctor.getString("profile_picture"), jsonObjectDoctor.getString("first_name"),
//                                        jsonObjectDoctor.getString("last_name"), jsonObjectDoctor.getString("full_name"),
//                                        jsonObjectMessage.getString("message"), jsonObjectMessage.getString("sender"),
//                                        jsonObjectMessage.getString("message_type"), jsonObjectMessage.getString("attachment"),
//                                        jsonObjectMessage.getString("time")));
//
//                            }
//                            if (messagesModels.size() > messagesSize) {
//                                messageAdapter.notifyDataSetChanged();
//                                rv_messages.smoothScrollToPosition(messagesModels.size() - 1);
//                            }
//                            count = 1;
//                        } else {
//                            if (count == 0) {
//                                myVdoctorApp.showToast(context, jsonParent.getString("msg"));
//                            }
//                            count = 1;
//                        }
//                    } catch (Exception e) {
//                        Log.e("exception", e.toString());
//                        if (count == 0) {
//                            myVdoctorApp.showToast(context, getResources().getString(R.string.unable_to_contact_server_please_try_again_later));
//                        }
//                        count = 1;
//                    }
//                }
//               /* if(srf_conversations.isRefreshing()){
//                    srf_conversations.setRefreshing(false);
//                }*/
//              /*  runOnUiThread(new Runnable() {
//                    // Language  store in shared preference
//                    @Override
//                    public void run() {
//                        final Handler handler = new Handler();
//
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                callConversationMessagesWebservice(false);
//                            }
//                        }, 1000);
//
//                    }
//                });*/
//
//            }
//
//        }).execute();

    }

    public void callSendMessageWebservice() {

 /*       RequestBody requestBody = new FormBody.Builder()
                .add("conversation_id", conversation_id)
                .add("sender", user_id)
                .add("message", edt_message.getText().toString())
                .add("type", "text")
                .build();*/
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        RequestBody requestBody = null;
        if (file != null) {
            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("conversation_id", conversation_id)
                    .addFormDataPart("sender", user_id)
                    .addFormDataPart("message", "")
                    .addFormDataPart("type", "image")
                    .addFormDataPart("file", file.getName().toString(), RequestBody.create(MEDIA_TYPE_PNG, file)).build();

        } else {
            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("conversation_id", conversation_id)
                    .addFormDataPart("sender", user_id)
                    .addFormDataPart("message", edt_message.getText().toString())
                    .addFormDataPart("type", "text").build();

        }
        new RestApiCall(this, true, ApiConstant.CONVERSATIONS_SEND, "post", false, requestBody, new RestApiCall.AsyncResponse() {

            @Override
            public void processFinish(String response) {
                if (response != null) {
                    Log.e("Response2", "Response Registration:" + response);
                    try {
                        JSONObject jsonParent = (JSONObject) new JSONTokener(response).nextValue();
                        String jsonStatus = jsonParent.getString("status");
                        if (jsonStatus.equals("success")) {
                            edt_message.setText("");
                            file = null;
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

    public void onSendButtonClick(View view) {
        callSendMessageWebservice();
    }

    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(mUpdateTimeTask);
        }
    }

    public void onResume() {
        super.onResume();
        if (handler != null) {
            handler.removeCallbacks(mUpdateTimeTask);
            handler.postDelayed(mUpdateTimeTask, 3000);
        }
    }

    public void onStop() {
        super.onStop();
        if (handler != null) {
            handler.removeCallbacks(mUpdateTimeTask);
        }
    }

    public void onDestroy() {
        super.onDestroy();
       /* if (counter != null) {
            counter.cancel();
        }*/
        if (handler != null) {
            handler.removeCallbacks(mUpdateTimeTask);

        }

    }

    public void openGallary(View view) {

        selectImage();

    }

    private void selectImage() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Add Photo!");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        arrayAdapter.add("Take Photo");
        arrayAdapter.add("Choose from Library");

        builderSingle.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    dialog.dismiss();
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_GET_CAMERA_FILE);
                } else if (i == 1) {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);

                }
            }
        });
        builderSingle.show();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        file = new File(path);
                        selectedImageUri = Uri.fromFile(file);
                        Log.e("selecet", file.getName().toString() + "");
                        callSendMessageWebservice();
                    }
                    // Set the image in ImageView
                    //ImageView((ImageView) findViewById(R.id.img_user)).setImageURI(selectedImageUri);
                } else if (requestCode == REQUEST_GET_CAMERA_FILE) {

                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/MyVDoctor";
                    File dir = new File(file_path);
                    if (!dir.exists())
                        dir.mkdirs();
                    file = new File(dir, "myvdoctor" + appPreference.getUserId() + ".png");
                    FileOutputStream fOut = new FileOutputStream(file);

                    photo.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                    callSendMessageWebservice();
                    Log.e("selecet", file.getName().toString() + "");

                    // Set the image in ImageView
                    //ImageView((ImageView) findViewById(R.id.img_user)).setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    public void callCheckWalletWebservice() {
        String doctorId = "";
        String patientId = "";
        if (appPreference.getType().equals("1")) {
            doctorId = appPreference.getDoctorId();
            patientId = patient_id;
        } else {
            doctorId = patient_id;
            patientId = appPreference.getDoctorId();
        }
        RequestBody requestBody = new FormBody.Builder()
                .add("patient_id", patientId)
                .add("doctor_id", doctorId)
                .add("type", "consultation")
                .build();

        String finalDoctorId = patientId;
        new RestApiCall(this, true, ApiConstant.CHECK , "post", false, requestBody, new RestApiCall.AsyncResponse() {

            @Override
            public void processFinish(String response) {
                if (response != null) {
                    Log.e("Response2", "Response Registration:" + response);
                    try {
                        JSONObject jsonParent = (JSONObject) new JSONTokener(response).nextValue();
                        String status = jsonParent.getString("status");
                        if (status.equals("true")) {

                            myVdoctorApp.showCallAlertDialog(context,fee,false,true, finalDoctorId, user_name, ApiConstant.PROFILE_PICTURE_PATH + user_profile_pic, user_id, appPreference.getFullName(), ApiConstant.PROFILE_PICTURE_PATH + appPreference.getProfilePicture(), true, null);

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


    public void callChatArchivedWebservice() {

        RequestBody requestBody = new FormBody.Builder()
                .add("conversation_id", conversation_id)
                .build();

        new RestApiCall(this, true, ApiConstant.CHAT_ARCHIVED, "post", false, requestBody, new RestApiCall.AsyncResponse() {

            @Override
            public void processFinish(String response) {
                if (response != null) {
                    Log.e("Response2", "Response Registration:" + response);
                    try {
                        JSONObject jsonParent = (JSONObject) new JSONTokener(response).nextValue();
                        String jsonStatus = jsonParent.getString("status");
                        if (jsonStatus.equals("success")) {
                            Intent resultIntent = new Intent();
                            // TODO Add extras or a data URI to this intent as appropriate.
                            resultIntent.putExtra("index", index);
                            setResult(Activity.RESULT_OK, resultIntent);
                            closeActivity();

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


}
