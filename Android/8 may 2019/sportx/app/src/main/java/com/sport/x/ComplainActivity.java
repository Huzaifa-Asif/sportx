package com.sport.x;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sport.x.Misc.Misc;
import com.sport.x.SharedPref.SharedPref;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

public class ComplainActivity extends AppCompatActivity {

    private EditText msg;
    private Button submit;
    Misc misc;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        setTitle("Complain");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);

        msg = findViewById(R.id.complain_msg);
        submit = findViewById(R.id.submit_complain);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComplain();
            }
        });
    }

    private void submitComplain(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Submitting Complain...");
        pd.setCancelable(false);
        pd.show();

        String message = msg.getText().toString();
        if(message.isEmpty()){
            misc.showToast("Please type your message");
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", message);
        jsonObject.addProperty("fk_user_id", sharedPref.getUserId());

        Ion.with(this)
                .load(misc.ROOT_PATH+"submit_complaint")
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
                            misc.showToast(result.getResult());
                            pd.dismiss();
                            onBackPressed();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllServiceActivity.class);
        startActivity(intent);
        finish();
    }
}
