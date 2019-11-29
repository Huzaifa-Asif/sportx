package com.sport.x.activities.serviceProviderActivities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.pedro.encoder.input.video.CameraOpenException;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.LiveStream;
import com.sport.x.SharedPref.SharedPref;
import com.sport.x.activities.menu.Menu;
import com.sport.x.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import net.ossrs.rtmp.ConnectCheckerRtmp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class StreamActivity extends Menu
        implements ConnectCheckerRtmp, View.OnClickListener, SurfaceHolder.Callback {
    SharedPref sharedPref;
    Misc misc;
    LiveStream currentStream=null;
    private RtmpCamera1 rtmpCamera1;
    private ImageButton button;
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String currentDateAndTime = "";
    private File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/rtmp-rtsp-stream-client-java");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.inflateView(R.layout.activity_sp_stream);
        setTitle("Start Streaming");
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
        sharedPref=new SharedPref(this);
        misc=new Misc(this);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        button = findViewById(R.id.b_start_stop);
        button.setOnClickListener(this);
        ImageButton switchCamera = findViewById(R.id.switch_camera);
        switchCamera.setOnClickListener(this);
        rtmpCamera1 = new RtmpCamera1(surfaceView, this);
        rtmpCamera1.setReTries(10);
        surfaceView.getHolder().addCallback(this);

    }

    @Override
    public void onConnectionSuccessRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StreamActivity.this, "Connection success", Toast.LENGTH_SHORT).show();
                callStartStreamWebService();
            }
        });


    }

    private void showPermissionsErrorAndRequest() {
        Toast.makeText(this, "You need permissions before", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onConnectionFailedRtmp(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rtmpCamera1.reTry(5000, reason)) {
                    Toast.makeText(StreamActivity.this, "Retry", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(StreamActivity.this, "Connection failed. " + reason, Toast.LENGTH_SHORT)
                            .show();
                    rtmpCamera1.stopStream();
                    button.setImageResource(R.drawable.start_stream_btn);
                    if(currentStream!=null)
                    {
                        callStopStreamWebService();
                    }
                }
            }
        });
    }

    @Override
    public void onNewBitrateRtmp(long bitrate) {

    }

    @Override
    public void onDisconnectRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StreamActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthErrorRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StreamActivity.this, "Auth error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthSuccessRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StreamActivity.this, "Auth success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_start_stop:
                if (!rtmpCamera1.isStreaming()) {
                    if (rtmpCamera1.isRecording()
                            || rtmpCamera1.prepareAudio() && rtmpCamera1.prepareVideo()) {
                        button.setImageResource(R.drawable.stop_stream_btn);
                        rtmpCamera1.startStream("rtmp://192.168.100.7:1935/live/"+sharedPref.getEmail());
                    } else {
                        Toast.makeText(this, "Error preparing stream, This device cant do it",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    button.setImageResource(R.drawable.start_stream_btn);
                    rtmpCamera1.stopStream();
                    if(currentStream!=null)
                    {
                        callStopStreamWebService();
                    }
                }
                break;
            case R.id.switch_camera:
                try {
                    rtmpCamera1.switchCamera();
                } catch (CameraOpenException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        rtmpCamera1.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (rtmpCamera1.isStreaming()) {
            rtmpCamera1.stopStream();
            button.setImageResource(R.drawable.start_stream_btn);

            if(currentStream!=null)
            {
                callStopStreamWebService();
            }
        }
        rtmpCamera1.stopPreview();
    }
    public void callStartStreamWebService()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Starting Stream.....");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("serviceProviderEmail", sharedPref.getEmail());
        jsonObject.addProperty("serviceProviderName", sharedPref.getName());
        jsonObject.addProperty("serviceProvider_picture_profile",sharedPref.getPicture());



        Ion.with(this)
                .load(misc.ROOT_PATH+"livestream/startlivestream")
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
                                button.setImageResource(R.drawable.start_stream_btn);
                                rtmpCamera1.stopStream();
                                if(currentStream!=null)
                                {
                                    callStopStreamWebService();
                                }
                                return;
                            }
                            else if (status) {
                                pd.dismiss();
                                String id = jsonObject1.getString("_id");
                                String email = jsonObject1.getString("serviceProviderEmail");
                                String name = jsonObject1.getString("serviceProviderName");
                                String picture = jsonObject1.getString("serviceProvider_picture_profile");
                                String date = jsonObject1.getString("date");
                                String time = jsonObject1.getString("time");
                                Boolean ongoing = jsonObject1.getBoolean("ongoing");
                                currentStream=new LiveStream(id,email,name,picture,date,time,ongoing);

                            }
                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();

                        }



                    }
                });

    }

    public void callStopStreamWebService()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Stopping Stream.....");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",currentStream.getId());



        Ion.with(this)
                .load(misc.ROOT_PATH+"livestream/stoplivestream")
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
                                currentStream=null;

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

        Intent intent = new Intent(this, com.sport.x.activities.serviceProviderActivities.HomeActivity.class);
        startActivity(intent);
        finish();

    }


}
