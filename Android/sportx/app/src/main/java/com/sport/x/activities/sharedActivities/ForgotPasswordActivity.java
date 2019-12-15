package com.sport.x.activities.sharedActivities;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sport.x.Misc.Misc;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText  email;
    private Button  reset;
    Misc misc;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_forgot_password);
        setTitle("Forgot Password");
        misc = new Misc(this);
        email = findViewById(R.id.reset_email);
        reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == reset.getId()){
            if(verifyEmail())
            {
                resetPassword();
            }

        }
    }

    private void resetPassword() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        JsonObject jsonObject = new JsonObject();
        user_email=email.getText().toString();
        jsonObject.addProperty("email", user_email);

        Ion.with(this)
                .load( misc.ROOT_PATH+"shared/resetPassword")
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null){
                            misc.showToast("Please check your connection");
                            pd.dismiss();
                            return;
                        }
                        else{
                            try {
                                JSONObject jsonObject1 = new JSONObject(result.getResult());
                                Boolean status = jsonObject1.getBoolean("status");
                                String message=jsonObject1.getString("Message");
                                if(status)
                                {
                                    pd.dismiss();
                                    misc.showToast(message);
                                }
                                else
                                {
                                    pd.dismiss();
                                    misc.showToast("Email not registered");
                                }
                            }
                            catch(JSONException e1) {
                                e1.printStackTrace();
                            }
                                pd.dismiss();

                        }
                    }
                });


    }

    private Boolean verifyEmail() {
        final String mail = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!mail.matches(emailPattern)){
            misc.showToast("Invalid Email");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
