package com.sport.x.ServiceProviderActivities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sport.x.LoginActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Service;
import com.sport.x.R;
import com.sport.x.RegisterAsActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterServiceActivity extends Menu implements View.OnClickListener {

    private Button register;
    private EditText name, email, phone, password, re_password, city,country,street_address;
    private CircleImageView image;
    private String bitmapTo64 = null;
    private static String resultPath = null;
    private final int REQUEST_CODE = 1;
    Misc misc;
    private ArrayList<String> selectedServices = new ArrayList<>();
    private ArrayList<Service> allServices = new ArrayList<>();
    private File uploadFile = null;
    private LatLng latlng=null;
    private String address;
    private GridLayout checkBoxLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_register_service);
        setTitle("Register As Service Provider");

        misc = new Misc(this);

        checkBoxLayout = findViewById(R.id.services);

        name = findViewById(R.id.provider_name);
        email = findViewById(R.id.provider_register_email);
        phone = findViewById(R.id.provider_phone);
        street_address = findViewById(R.id.provider_address);
        city=findViewById(R.id.provider_address);
        country=findViewById(R.id.provider_country);
        re_password = findViewById(R.id.provider_confirm_password);
        password = findViewById(R.id.provider_password);
//
        image = findViewById(R.id.profile_pic_service);
        image.setOnClickListener(this);

        register = findViewById(R.id.provider_register_button);
        register.setOnClickListener(this);

        if(misc.isConnectedToInternet()){
            fetchServices();
        }
        else{
            misc.showToast("No Internet Connection");
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == image.getId()) {
            ActivityCompat.requestPermissions
                    (RegisterServiceActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if(v.getId() == register.getId()){
            if(misc.isConnectedToInternet()){
                registerVendor();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
            else{
                Toast.makeText(this, "You don't have permissions", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null){
            Toast.makeText(getApplicationContext(), "Please Select Profile Image", Toast.LENGTH_LONG).show();
            return;
        }

        Uri selectedImageUri = data.getData( );
        String picturePath = getPath(getApplicationContext(), selectedImageUri );

        try{

            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmapTo64 = bitmapToBase64(bitmap);
            Log.d("Converted Image", bitmapTo64);
            image.setImageBitmap(bitmap);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getPath(Context context, Uri uri ) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                resultPath = cursor.getString( column_index );

            }
            cursor.close( );
        }
        if(resultPath == null) {
            resultPath = null;
        }
        return resultPath;
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    private void fetchServices(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Services");
        pd.setCancelable(false);
        pd.show();

        Ion.with(this)
                .load(misc.ROOT_PATH+"serviceCategory/get_serviceCategory")
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null) {
                            misc.showToast("Please check your connection");
                            pd.dismiss();
                            return;
                        }

                        try {

//                            JSONObject jsonObject1 = new JSONObject(result.getResult());

                            JSONArray serviceArray = new JSONArray(result.getResult());
                            if(serviceArray.length() < 1) {
                                misc.showToast("No Services Found");
                                pd.dismiss();
                            }
                            else{
                                allServices.clear();
                                for (int i = 0; i < serviceArray.length(); i++) {
                                    JSONObject serviceObject = (JSONObject) serviceArray.get(i);
                                    String serviceName = serviceObject.getString("name");
                                    String serviceId = serviceObject.getString("_id");
                                    String serviceImage = serviceObject.getString("picture");
                                    allServices.add(new Service(serviceId, serviceName, serviceImage));


                                    final CheckBox cb = new CheckBox(getApplicationContext());
                                    cb.setText(serviceName.toUpperCase());
                                    cb.setTextColor(R.color.colorPrimary);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        cb.setButtonTintList(ColorStateList.valueOf(R.color.colorPrimary));
                                    }
                                    cb.setId(i);
                                    cb.setTextSize(10);
                                    cb.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String selectedItem = String.valueOf(cb.getText());
                                            if(selectedServices.contains(selectedItem)){
                                                selectedServices.remove(selectedItem);
                                            }
                                            else{
                                                selectedServices.add(selectedItem);
                                            }
                                        }
                                    });

                                    checkBoxLayout.addView(cb);
                                    pd.dismiss();
                                }
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    private boolean validate(){

        String user_name = name.getText().toString().toLowerCase().trim();
        String user_email = email.getText().toString().toLowerCase().trim();
        String user_phone = phone.getText().toString().toLowerCase().trim();
        String user_password = password.getText().toString();
        String user_re_password = re_password.getText().toString();
        String user_street_address=street_address.getText().toString();
        String user_city=city.getText().toString();
        String user_country=country.getText().toString();
        address=street_address.getText().toString()+", "+city.getText().toString()+", "+country.getText().toString();
        latlng = misc.getCoordinates(address);


        String regex = "[A-Za-z A-Za-z]+";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(user_name.length() < 3 && !user_name.matches(regex)){
            misc.showToast("Invalid Name");
            name.setError("Invalid Name");
            return false;
        }
        if(!user_email.matches(emailPattern) && user_email.isEmpty()) {
            misc.showToast("Invalid Email");
            email.setError("Invalid Email");
            return false;
        }
        if(user_phone.length() < 11) {
            misc.showToast("Invalid Phone Number");
            phone.setError("Invalid Phone Number");
            return false;
        }
        if(user_password.length() < 6 ) {
            misc.showToast("Password should be min 6 characters");
            password.setError("Password should be min 6 characters");
            return false;
        }
        if(!user_password.equals(user_re_password)) {
            misc.showToast("Password Mismatch");
            re_password.setError("Password Mismatch");
            return false;
        }
        if(user_street_address.length() < 5) {
            misc.showToast("Please Enter Full Street Address");
            street_address.setError("Please Enter Full Street Address");
            return false;
        }
        if(user_city.isEmpty()) {
            misc.showToast("Please Enter City Name");
            street_address.setError("Please Enter City Name");
            return false;
        }
        if(user_country.isEmpty()) {
            misc.showToast("Please Enter Country Name");
            street_address.setError("Please Enter Country Name");
            return false;
        }
        if(latlng == null) {
            misc.showToast("Service Location Not Found");
            return false;
        }
        if(selectedServices.size() > 1) {
            misc.showToast("Please select only one Service");
            return false;
        }


        return true;
    }

    private void registerVendor(){
        if(validate()) {
            if(resultPath != null) {
                uploadFile = new File(resultPath);
                registerWithImage();
            }
            else{
                registerWithoutImage();
            }
        }
    }

    private void registerWithImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Registration in process...");
        pd.setCancelable(false);
        pd.show();

        String items = "";
        for(String item : selectedServices){
            items += item;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name.getText().toString().trim());
        jsonObject.addProperty("email", email.getText().toString().trim());
        jsonObject.addProperty("contact", phone.getText().toString().trim());
        jsonObject.addProperty("address", address);
        jsonObject.addProperty("password", password.getText().toString());
        jsonObject.addProperty("category", items);
        jsonObject.addProperty("picture_profile", bitmapTo64);
        jsonObject.addProperty("long", latlng.longitude);
        jsonObject.addProperty("lat", latlng.latitude);


        Ion.with(this)
                .load(misc.ROOT_PATH+"serviceProvider/signup_serviceProvider")
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        resultPath = null;
                        if(e != null){
                            misc.showToast("Internet Connection Problem");
                            pd.dismiss();
                            return;
                        }
                        String response = result.getResult();
                        if(response.isEmpty()){
                            pd.dismiss();
                            misc.showToast("Email or Phone already exists");
                            return;
                        }
                        else{
                            pd.dismiss();
//                            misc.showToast(result.getResult());
                            misc.showToast("Signup Successful");
                            Intent intent = new Intent(RegisterServiceActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private void registerWithoutImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Registration in process...");
        pd.setCancelable(false);
        pd.show();

        String items = "";
        for(String item : selectedServices){
            items += item;
        }
//
//        JsonObject abc = new JsonObject();
//        abc.addProperty("long", lon1);
//        abc.addProperty("lat", lat1);




        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name.getText().toString().trim());
        jsonObject.addProperty("email", email.getText().toString().trim());
        jsonObject.addProperty("contact", phone.getText().toString().trim());
        jsonObject.addProperty("address", address);
        jsonObject.addProperty("password", password.getText().toString());
        jsonObject.addProperty("category", items);
        jsonObject.addProperty("long", latlng.longitude);
        jsonObject.addProperty("lat", latlng.latitude);


        Ion.with(this)
                .load(misc.ROOT_PATH+"serviceprovider/signup_serviceProvider")
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        resultPath = null;
                        if(e != null){
                            misc.showToast("Internet Connection Problem");
                            pd.dismiss();
                            return;
                        }
                        String response = result.getResult();
                        if(response.isEmpty()){
                            pd.dismiss();
                            misc.showToast("Email or Phone already exists");
                            return;
                        }
                        else{
                            pd.dismiss();
//                            misc.showToast(result.getResult());
                            misc.showToast("Signup Successful");
                            Intent intent = new Intent(RegisterServiceActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ServiceHomeActivity.class);
        startActivity(intent);
        finish();
    }


}
