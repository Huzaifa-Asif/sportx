package com.sportx.pk.activities.sharedActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.sportx.pk.activities.customerActivities.HomeActivity;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.activities.menu.Menu;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ComplainActivity extends Menu {

    private EditText msg;
    private Button submit;
    Misc misc;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sh_complain);
        setTitle("Complain");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);

        msg = findViewById(R.id.complain_msg);
        submit = findViewById(R.id.submit_complain);

        submit.setOnClickListener(v -> submitComplain());
    }

    private void submitComplain(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Submitting Complaint...");
        pd.setCancelable(false);
        pd.show();

        String message = msg.getText().toString();
        if(message.isEmpty()){
            misc.showToast("Please type your message");
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("complaint", message);
        jsonObject.addProperty("complainantEmail", sharedPref.getUserId());

        Ion.with(this)
                .load(misc.ROOT_PATH+"complaint/add_complaint")
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null) {
                            misc.showToast("Please check your connection");
                            pd.dismiss();
                            return;
                        }
                        else{
                            pd.dismiss();

                            try{
                                JSONObject jsonObject1 = new JSONObject(result.getResult());

                                Boolean status = jsonObject1.getBoolean("status");


                                if (!status) {
                                    String Message = jsonObject1.getString("Message");
                                    misc.showToast(Message);
                                    return;
                                }
                                else if (status) {
                                    misc.showToast("Complaint Submitted Successfully");
                                    Intent intent = new Intent(ComplainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                            onBackPressed();
                        }
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
