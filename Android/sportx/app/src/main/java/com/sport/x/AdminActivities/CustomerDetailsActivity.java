package com.sport.x.AdminActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sport.x.Misc.Misc;
import com.sport.x.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name, cnic, phone, email;
    private CircularImageView image;
    private Button button;
    Misc misc;
    private String id, status;
    private int vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_details);

        misc = new Misc(this);

        name = findViewById(R.id.customer_name);
        email = findViewById(R.id.customer_email);
        phone = findViewById(R.id.customer_phone);
        cnic = findViewById(R.id.customer_cnic);

        image = findViewById(R.id.customer_image);
        button = findViewById(R.id.change_status);

        Intent intent = getIntent();
        vendor = intent.getIntExtra("vendors", 0);
        name.setText("Name: " + intent.getStringExtra("name"));
        email.setText("Email: " + intent.getStringExtra("email"));
        phone.setText("Phone: " + intent.getStringExtra("phone"));
        cnic.setText("CNIC: " + intent.getStringExtra("cnic"));
        String imageFile = intent.getStringExtra("image");

        if(imageFile.isEmpty()) {
            image.setImageResource(R.drawable.usersicon);
        }
        else{
            Picasso.get()
                    .load(imageFile)
                    .into(image);
        }
        setTitle(intent.getStringExtra("name").toUpperCase());

        id = intent.getStringExtra("id");
        status = intent.getStringExtra("status");

        if(status.equals("enabled")){
            button.setText("Disable " + intent.getStringExtra("name").toUpperCase());
        }
        else{
            button.setText("Enable " + intent.getStringExtra("name").toUpperCase());
        }

        if(status.equals("enabled")){
            status = "disabled";
        }
        else{
            status = "enabled";
        }


        button.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        if(vendor == 1) {
            Intent intent = new Intent(this, AllServiceProvidersActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, AllCustomersActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(misc.isConnectedToInternet()){
            changeStatus();
        }
        else{
            misc.showToast("No Internet Connection");
        }
    }

    private void changeStatus(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", status);

        Ion.with(this)
                .load("PUT", misc.ROOT_PATH+"change_status/"+id)
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e!=null){
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
}
