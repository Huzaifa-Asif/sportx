package com.sport.x.activities.sharedActivities;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sport.x.activities.customerActivities.HomeActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login, register;
    private TextView forgot;
    private EditText user_email, user_password;
    Misc misc;
    SharedPref sharedPref;


    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_login);
        setTitle("Login");

        sharedPref = new SharedPref(this);
        misc = new Misc(this);

        user_email = findViewById(R.id.login_email);
        user_password = findViewById(R.id.login_password);
        forgot = findViewById(R.id.forgot_password);

      //  loginButton = findViewById(R.id.login_fb);

        forgot.setOnClickListener(this);

        login = findViewById(R.id.login_button);
        register = findViewById(R.id.create_account);
        register.setOnClickListener(this);
        login.setOnClickListener(this);

        checkLogin();
    }

    private void checkLogin(){
        String _id = sharedPref.getUserId();
        Integer role = sharedPref.getUserRole();
        boolean profileCompleted=sharedPref.getProfileCompleted();
        if(_id != null && role != null) {
            if(role==2) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            if(role==1) {
                if(!profileCompleted)
                {
                    Intent intent = new Intent(LoginActivity.this, com.sport.x.activities.serviceProviderActivities.AddBookingSettingsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(LoginActivity.this, com.sport.x.activities.serviceProviderActivities.HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == register.getId()){
            Intent intent = new Intent(this, SignupAsActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId() == forgot.getId()) {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId() == login.getId())
        {
            String email = user_email.getText().toString().trim();
            String password = user_password.getText().toString().trim();

            if(misc.isConnectedToInternet()){
                if(email.isEmpty() || password.isEmpty()) {
                    misc.showToast("Email and Password required!");
                    return;
                    }
                    loginUser(email, password);
                }

        }
    }

    private void loginUser(String email, String password){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Logging in...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);

        Ion.with(this)
                .load(misc.ROOT_PATH+"shared/login")
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null) {
                            pd.dismiss();
                            misc.showToast("Please check internet connection");
                            return;
                        }

                        else{
                            try {


                                JSONObject jsonObject1 = new JSONObject(result.getResult());

                                Boolean status = jsonObject1.getBoolean("status");




                               if(status==true)
                                {

                                    String id = jsonObject1.getString("_id");
                                    String email = jsonObject1.getString("email");
                                    Integer role = jsonObject1.getInt("role");
                                    String name = jsonObject1.getString("name");
                                    String contact = jsonObject1.getString("contact");

                                    // Customer
                                    if(role == 2)
                                    {
                                        String address="";
                                        String picture = jsonObject1.getString("picture");
                                    sharedPref.createLoginSession(id, email,address, role, name, contact, picture);
                                    pd.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    }

                                    // Service Provider
                                    else if(role == 1 )
                                    {
                                        boolean profileCompleted=jsonObject1.getBoolean("profile_completed");
                                        String picture_profile = jsonObject1.getString("picture_profile");
                                        String address = jsonObject1.getString("address");
                                        sharedPref.createLoginSession(id, email, address, role, name, contact, picture_profile);
                                        sharedPref.setProfileCompleted(profileCompleted);
                                    pd.dismiss();
                                    if(!profileCompleted)
                                    {
                                        Intent intent = new Intent(LoginActivity.this, com.sport.x.activities.serviceProviderActivities.AddBookingSettingsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(LoginActivity.this, com.sport.x.activities.serviceProviderActivities.HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    }

                                }

                               else if(status==false){
                                   pd.dismiss();
                                   misc.showToast("Invalid Email or Password!");
                                   return;
                               }



                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }



}
