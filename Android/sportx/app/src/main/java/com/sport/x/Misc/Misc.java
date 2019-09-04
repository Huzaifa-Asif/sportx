package com.sport.x.Misc;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.share.Share;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Misc {

    private Context context;
    private boolean connected;
    private double lat, lon;
    SharedPref SharedPref;
    Misc misc;

    public static final String ROOT_PATH = "https://sport-x.herokuapp.com/";
//    public static final String ROOT_PATH = "http://192.168.1.7:3300/";

    public Misc(Context context) {
        this.context = context;
        SharedPref=new SharedPref(this.context);

    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            Toast.makeText(context, "Internet Connection not available!", Toast.LENGTH_SHORT).show();
            connected = false;
        }
        return connected;
    }

    public LatLng getCoordinates(String location) {
        Geocoder gc = new Geocoder(context);
        LatLng latLng = null;
        try {
            List<Address> address = gc.getFromLocationName(location, 1);
            Address add = address.get(0);
            lat = add.getLatitude();
            lon = add.getLongitude();
            latLng = new LatLng(lat, lon);
        } catch (IOException e) {
            showToast("Service Location not found");
            e.printStackTrace();
        }
        return latLng;
    }

    public void showToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void addToken(String token){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", token);
        misc=new Misc(context);
        if(SharedPref.getUserRole()==1)
        {
            Ion.with(context)
                    .load("PATCH", misc.ROOT_PATH+"update_serviceProvider/"+SharedPref.getEmail())
                    .setJsonObjectBody(jsonObject)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>() {
                        @Override
                        public void onCompleted(Exception e, Response<String> result) {
                            if (e != null) {
                                Log.wtf("msg","No Internet");
                                return;
                            }


                            try{
                                JSONObject jsonObject1 = new JSONObject(result.getResult());

                                Boolean status = jsonObject1.getBoolean("status");


                                if (!status) {
                                    String Message = jsonObject1.getString("Message");
                                    Log.wtf("msg",Message);
                                    return;
                                }


                            }
                            catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }
        else if(SharedPref.getUserRole()==2)
        {
            Ion.with(context)
                    .load("PATCH",misc.ROOT_PATH+"update_customer/"+SharedPref.getEmail())
                    .setJsonObjectBody(jsonObject)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>() {
                        @Override
                        public void onCompleted(Exception e, Response<String> result) {
                            if (e != null) {
                                misc.showToast("Please check your connection");
                                return;
                            }


                            try{
                                JSONObject jsonObject1 = new JSONObject(result.getResult());

                                Boolean status = jsonObject1.getBoolean("status");


                                if (!status) {
                                    String Message = jsonObject1.getString("Message");
                                    misc.showToast(Message);
                                    return;
                                }


                            }
                            catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }


    }
    public void saveCurrentToken()
    {
        misc=new Misc(context);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.wtf( "message", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();


                        misc.addToken(token);
                    }
                });
    }
}
