package com.sport.x;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sport.x.Misc.Misc;
import com.sport.x.Activities.ServiceProviderActivities.Menu;
import com.sport.x.SharedPref.SharedPref;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;


public class PasswordUpdate extends Menu {
    private EditText  password, confirm;
    private Button update;
    Misc misc;
    SharedPref sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.password_update);
        setTitle("Password Update");
        password = findViewById(R.id.up_password);
        confirm = findViewById(R.id.up_confirm_password);


        update = findViewById(R.id.update_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!misc.isConnectedToInternet()){
                    misc.showToast("No Internet Connection");
                }
                else{
                    updateProfile();
                }
            }
        });

        misc = new Misc(this);
        sharedPref = new SharedPref(this);

        if(misc.isConnectedToInternet()) {

        }
        else{
            misc.showToast("No Internet Connection");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllServiceActivity.class);
        startActivity(intent);
        finish();
    }



    private boolean validatePassword(){

        String user_password = password.getText().toString();
        String user_re_password = confirm.getText().toString();


        if(user_password.length() < 6 ) {
            misc.showToast("Password should be min 6 characters");
            password.setError("Password should be min 6 characters");
            return false;
        }
        if(!user_password.equalsIgnoreCase(user_re_password)) {
            misc.showToast("Password Mismatch");
            confirm.setError("Password Mismatch");
            return false;
        }

        return true;
    }

    private void updateProfile(){
        if(validatePassword()){

           updatePassword();
        }
    }


    private void updatePassword(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Updating Password...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password.getText().toString());


        Ion.with(this)
                .load("PATCH", misc.ROOT_PATH+"customer/update_customer/"+sharedPref.getEmail())
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
                            JSONObject jsonObject2 = new JSONObject(result.getResult());

                            Boolean status = jsonObject2.getBoolean("status");


                            if (!status) {
                                String Message = jsonObject2.getString("Message");
                                pd.dismiss();
                                misc.showToast(Message);
                                return;
                            }
                            else if (status) {
                                pd.dismiss();
                                misc.showToast("Password Updated Successfully");
                                Intent intent = new Intent(PasswordUpdate.this, AllServiceActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }
                });

    }

}
