package com.sport.x.Activities.CustomerActivities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sport.x.Misc.Misc;
import com.sport.x.Activities.Menu.Menu;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;


public class UpdateProfileActivity extends Menu {

    private EditText name, email, phone;
    private ImageView image;
    private Button update;
    private static String resultPath = null;
    private final int REQUEST_CODE = 1;
    Misc misc;
    SharedPref sharedPref;
    private File uploadFile = null;

    private String bitmapTo64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_c_update_profile);
        setTitle("Profile");

        name = findViewById(R.id.up_full_name);
        email = findViewById(R.id.update_email);
        phone = findViewById(R.id.up_phone);

        email.setEnabled(false);

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

        image = findViewById(R.id.profile_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions
                        (UpdateProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        });

        misc = new Misc(this);
        sharedPref = new SharedPref(this);

        if(misc.isConnectedToInternet()) {
            fetchUserProfile();
        }
        else{
            misc.showToast("No Internet Connection");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
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
            Log.d("Converted Image: ", bitmapTo64);

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



    private boolean validate(){

        String user_name = name.getText().toString().trim();
        String user_email = email.getText().toString().trim();
        String user_phone = phone.getText().toString().trim();

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

        return true;
    }



    private void fetchUserProfile(){

        if((sharedPref.getPicture())==null)
        {
            image.setImageResource(R.drawable.user);

        }
        else
            {
            Ion.with(getApplicationContext()).load(sharedPref.getPicture().replace("\"","")).intoImageView(image);
        }

                                name.setText(sharedPref.getName());
                                email.setText(sharedPref.getEmail());
                                phone.setText(sharedPref.getContact());

    }

    private void updateProfile(){
        if(validate()){

            if(resultPath != null) {
                uploadFile = new File(resultPath);
                updateWithImage();
            }
            else{
                updateWithoutImage();
            }
        }
    }

    private void updateWithImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Updating Profile...");
        pd.setCancelable(false);
        pd.show();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name.getText().toString().trim());
        jsonObject.addProperty("contact", phone.getText().toString());
        jsonObject.addProperty("picture", bitmapTo64);



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
                                String id = jsonObject2.getString("_id");
                                String email = jsonObject2.getString("email");
                                int role = jsonObject2.getInt("role");
                                String name = jsonObject2.getString("name");
                                String contact = jsonObject2.getString("contact");
                                String picture = jsonObject2.getString("picture");
                                String address="";
                                pd.dismiss();
                                sharedPref.createLoginSession(id, email,address, role, name, contact, picture);
                                misc.showToast("Profile Updated Successfully");
                                Intent intent = new Intent(UpdateProfileActivity.this, HomeActivity.class);
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

    private void updateWithoutImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Updating Profile...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name.getText().toString().trim());
        jsonObject.addProperty("contact", phone.getText().toString());



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
                                String id = jsonObject2.getString("_id");
                                String email = jsonObject2.getString("email");
                                int role = jsonObject2.getInt("role");
                                String name = jsonObject2.getString("name");
                                String contact = jsonObject2.getString("contact");
                                String picture = jsonObject2.getString("picture");
                                String address="";
                               sharedPref.createLoginSession(id, email,address, role, name, contact, picture);

                                pd.dismiss();
                                misc.showToast("Profile Updated Successfully");
                                Intent intent = new Intent(UpdateProfileActivity.this, HomeActivity.class);
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





    private void base64ToBitmap(String b64) {
        misc.showToast(b64);
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        image.setImageBitmap(bitmap);
    }
}
