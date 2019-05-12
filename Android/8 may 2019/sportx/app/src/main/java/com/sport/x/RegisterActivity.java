package com.sport.x;

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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sport.x.Misc.Misc;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, phone, password, re_password, cnic;
    private RadioButton male, female;
    private Button register;
    private CircleImageView image;
    private String bitmapTo64, selectedGender = null;
    private static String resultPath = null;
    private final int REQUEST_CODE = 1;
    private String user_role = "customer";
    private File uploadFile = null;
    Misc misc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register As Customer");

        misc = new Misc(this);

        image = findViewById(R.id.profile_pic_customer);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions
                        (RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        });

        name = findViewById(R.id.full_name);
        phone = findViewById(R.id.reg_phone);
        email = findViewById(R.id.register_email);
    //    cnic = findViewById(R.id.cus_reg_cnic);
        password = findViewById(R.id.reg_password);
        re_password = findViewById(R.id.confirm_password);

        male = findViewById(R.id.cus_male);
        female = findViewById(R.id.cus_female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(male.isChecked()){
                    selectedGender = male.getText().toString();
                }
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(female.isChecked()) {
                    selectedGender = female.getText().toString();
                }
            }
        });

        if(male.isChecked()){
            selectedGender = male.getText().toString();
        }
        if(female.isChecked()) {
            selectedGender = female.getText().toString();
        }

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
        String user_cnic = cnic.getText().toString();
        String user_re_password = re_password.getText().toString();

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
        if(!user_password.equalsIgnoreCase(user_re_password)) {
            misc.showToast("Password Mismatch");
            re_password.setError("Password Mismatch");
            return false;
        }

//        if(user_cnic.length() < 13) {
//            misc.showToast("Invalid CNIC");
//            cnic.setError("Invalid CNIC");
//            return false;
//        }

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

        Ion.with(this)
                .load(misc.ROOT_PATH + "create_user")
                .setMultipartFile("user_image", uploadFile)
                .setMultipartParameter("user_name", name.getText().toString().trim())
                .setMultipartParameter("user_email", email.getText().toString().trim())
                .setMultipartParameter("user_phone", phone.getText().toString().trim())
                .setMultipartParameter("user_cnic", cnic.getText().toString().trim())
                .setMultipartParameter("user_password", password.getText().toString())
                .setMultipartParameter("user_role", "customer")
                .setMultipartParameter("user_gender", selectedGender)
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
                        String response = result.getResult();
                        if (response.isEmpty()) {
                            pd.dismiss();
                            misc.showToast("Email, Phone, or CNIC already exists");
                            return;
                        } else {
                            pd.dismiss();
                            misc.showToast(result.getResult());
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_name", name.getText().toString().trim());
        jsonObject.addProperty("user_email", email.getText().toString().trim());
        jsonObject.addProperty("user_phone", phone.getText().toString().trim());
        jsonObject.addProperty("user_cnic", cnic.getText().toString().trim());
        jsonObject.addProperty("user_password", password.getText().toString());
        jsonObject.addProperty("user_role", "customer");
        jsonObject.addProperty("user_gender", selectedGender);

        Ion.with(this)
                .load(misc.ROOT_PATH+"new_user")
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
                        String response = result.getResult();
                        if (response.isEmpty()) {
                            pd.dismiss();
                            misc.showToast("Email, Phone, or CNIC already exists");
                            return;
                        } else {
                            pd.dismiss();
                            misc.showToast(result.getResult());
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
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
        Intent intent = new Intent(this, RegisterAsActivity.class);
        startActivity(intent);
        finish();
    }
}
