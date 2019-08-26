package com.sport.x;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sport.x.AdminActivities.AllJobsActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.ServiceProviderActivities.ProviderJobsActivity;
import com.sport.x.SharedPref.SharedPref;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;

public class CustomerCompletedJobDetailsActivity extends AppCompatActivity {

    private TextView completed, text1, text2, text3;
    private String job_id, customerEmail, serviceEmail, serviceName;
    Misc misc;
    private RatingBar ratingBar;
    private EditText review;
    private Button submit;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_completed_job_details);
        setTitle("Job Details");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);

        int userRole = sharedPref.getUserRole();


        text3 = findViewById(R.id.rate_service);
        ratingBar = findViewById(R.id.rate_job);
        review = findViewById(R.id.feedback);
        completed = findViewById(R.id.job_completed);
        submit = findViewById(R.id.rate);


        Intent intent = getIntent();
        completed.setText("Job Completed By " + intent.getStringExtra("serviceProviderName"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(misc.isConnectedToInternet()){
                    postRating();
                }
                else{
                    misc.showToast("No Internet Connection");
                }

            }
        });


        text2 = findViewById(R.id.rate_service);

        job_id = intent.getStringExtra("job_id");
        serviceEmail = intent.getStringExtra("serviceProviderEmail");
        serviceName = intent.getStringExtra("serviceProviderName");
        customerEmail = intent.getStringExtra("customerEmail");
        String name = intent.getStringExtra("serviceProviderName");
        String service = intent.getStringExtra("service_name");
        text2.setText("Rate " + name + " for his work");

        if(userRole == 1) {
            submit.setVisibility(GONE);
            ratingBar.setEnabled(false);
            review.setEnabled(false);
            text2.setText("Job Rating");
        }

        if(sharedPref.getUserId() == null) {
            text3.setText("Job Rating");
            review.setHint("");
            submit.setVisibility(GONE);
            review.setEnabled(false);
            ratingBar.setEnabled(false);
        }

        if(misc.isConnectedToInternet()) {
            findRating();
        }
        else{
            misc.showToast("No Internet Connection");
        }
    }

    private void findRating(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait... ");
        pd.setCancelable(true);
        pd.show();

        Ion.with(this)
                .load(misc.ROOT_PATH+"findRating/"+job_id)
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
                            if(!result.getResult().isEmpty()){
                                try {
                                    JSONArray jsonArray = new JSONArray(result.getResult());
                                    if(jsonArray.length() < 1) {
                                        pd.dismiss();
                                    }
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                                    String ratingStars = jsonObject.getString("rating");
                                    String comment = jsonObject.getString("feedback");

                                    ratingBar.setRating(Float.parseFloat(ratingStars));
                                    review.setText(comment);

                                    ratingBar.setEnabled(false);
                                    review.setEnabled(false);

                                    submit.setVisibility(GONE);
                                    pd.dismiss();
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            else{
                                pd.dismiss();
                            }
                        }
                    }
                });
    }

    private void postRating(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait... ");
        pd.setCancelable(false);
        pd.show();

        if(!validate()){
            misc.showToast("Rating and feedback required");
            return;
        }

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        JsonObject rating = new JsonObject();
        rating.addProperty("rating", ratingBar.getRating());
        rating.addProperty("feedback", review.getText().toString());
        rating.addProperty("date", formattedDate);
        rating.addProperty("jobId", job_id);
        rating.addProperty("customerEmail", customerEmail);
        rating.addProperty("serviceProviderEmail", serviceEmail);
        rating.addProperty("serviceProviderName", serviceName);

        Ion.with(this)
                .load(misc.ROOT_PATH+"post_rating")
                .setJsonObjectBody(rating)
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
                            try {
                                JSONObject jsonObject = new JSONObject(result.getResult());
                                String message = jsonObject.getString("message");
                                misc.showToast(message);
                                pd.dismiss();
                                onBackPressed();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                });
    }

    private boolean validate(){
        String ratings = String.valueOf(ratingBar.getRating());
        String comment = review.getText().toString();

        if(ratings.isEmpty()) {
            misc.showToast("Please Rate Service");
            return false;
        }
        if(comment.isEmpty()) {
            misc.showToast("Please enter feedback");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(sharedPref.getUserId() == null) {
            Intent intent = new Intent(this, AllJobsActivity.class);
            startActivity(intent);
            finish();
        }
        if(sharedPref.getUserRole() == 1) {
            Intent intent = new Intent(this, ProviderJobsActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, JobHistoryActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
