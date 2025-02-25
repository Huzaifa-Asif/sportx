package com.sportx.pk.activities.customerActivities;

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
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sportx.pk.activities.sharedActivities.LoginActivity;
import com.sportx.pk.activities.sharedActivities.SignupAsActivity;
import com.sportx.pk.Misc.Misc;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sportx.pk.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity {

    private EditText name, email, phone, password, re_password;
    private RadioButton male, female;
    private Button register;
    private CircleImageView image;
    private String bitmapTo64, selectedGender = null;
    private static String resultPath = null;
    private final int REQUEST_CODE = 1;
    private File uploadFile = null;
    Misc misc;
    String imagebase64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_signup);
        setTitle("Register As Customer");

        misc = new Misc(this);

        image = findViewById(R.id.profile_pic_customer);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions
                        (SignupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        });

        name = findViewById(R.id.full_name);
        phone = findViewById(R.id.reg_phone);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.reg_password);
        re_password = findViewById(R.id.confirm_password);


        register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(misc.isConnectedToInternet()){
                    registerCustomer();
                }
            }
        });
    }

    private boolean validate(){

        String user_name = name.getText().toString().trim();
        String user_email = email.getText().toString().trim();
        String user_phone = phone.getText().toString().trim();
        String user_password = password.getText().toString();
        String user_re_password = re_password.getText().toString();
        String passwordPattern= "^[a-zA-Z0-9]*$";
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
        if(!user_password.matches(passwordPattern))
        {
            misc.showToast("Password should only contain Alphabets and Numbers");
            password.setError("Password should only contain Alphabets and Numbers");
            return false;
        }
        if(user_password.length() < 6 ) {
            misc.showToast("Password should be min 6 characters");
            password.setError("Password should be min 6 characters");
            return false;
        }
        if(!user_password.equalsIgnoreCase(user_re_password)) {
            misc.showToast("Password Mismatch");
            re_password.setError("Password Mismatch");
            return false;
        }


        return true;
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
            imagebase64 = bitmapTo64.toString();
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

    private void registerCustomer(){

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

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name.getText().toString().trim());
        jsonObject.addProperty("picture", bitmapTo64);
        jsonObject.addProperty("email", email.getText().toString().trim());
        jsonObject.addProperty("contact", phone.getText().toString().trim());
        jsonObject.addProperty("password", password.getText().toString());
//        jsonObject.addProperty("user_gender", selectedGender);

        Ion.with(this)
                .load(misc.ROOT_PATH+"customer/signup_customer")
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
                                misc.showToast("Signup Successful");
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
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

    private void registerWithoutImage(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Registration in process...");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name.getText().toString().trim());
        jsonObject.addProperty("email", email.getText().toString().trim());
        jsonObject.addProperty("contact", phone.getText().toString().trim());
        jsonObject.addProperty("password", password.getText().toString());
//        jsonObject.addProperty("user_gender", selectedGender);

        Ion.with(this)
                .load(misc.ROOT_PATH+"customer/signup_customer")
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
                                    misc.showToast("Signup Successful");
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
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

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SignupAsActivity.class);
        startActivity(intent);
        finish();
    }
}
