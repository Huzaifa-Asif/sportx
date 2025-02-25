package com.sportx.pk.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sportx.pk.Adapters.InProgressJobsAdapter;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.Job;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerInProgressJobs extends Fragment {

    private Context context;
    Misc misc;
    SharedPref sharedPref;
    private ArrayList<Job> jobsListModel;
    InProgressJobsAdapter jobsAdapter;
    private RecyclerView view;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.customer_in_progress_jobs, container, false);

        context = getActivity();
        misc = new Misc(context);
        sharedPref = new SharedPref(context);

        jobsListModel = new ArrayList<>();
        jobsAdapter = new InProgressJobsAdapter(context, jobsListModel);

        textView = rootView.findViewById(R.id.no_ip);

        view = rootView.findViewById(R.id.in_progress_jobs);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.setAdapter(jobsAdapter);

        if(misc.isConnectedToInternet()) {
            inProgressJobs();
        }
        else{
            misc.showToast("No Internet Connection");
        }
        return rootView;
    }

    private void inProgressJobs() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please wait... ");
        pd.setCancelable(false);
        pd.show();
        Ion.with(context)
                .load(misc.ROOT_PATH+"bookingdetails/customerInProgressBookings/"+sharedPref.getEmail())
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
                                JSONArray jsonArray = new JSONArray(result.getResult());
                                if(jsonArray.length() < 1) {
                                    textView.setVisibility(View.VISIBLE);
                                    pd.dismiss();
                                    return;
                                }
                                jobsListModel.clear();
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    String date = jsonObject.getString("date");
                                    String job_id = jsonObject.getString("_id");
                                    String bookingType = jsonObject.getString("bookingType");
                                    String state = jsonObject.getString("state");
                                    String time = jsonObject.getString("time");
                                    String serviceProviderEmail = jsonObject.getString("serviceProviderEmail");
                                    String serviceProviderName = jsonObject.getString("serviceProviderName");
                                    String serviceProviderNumber = jsonObject.getString("serviceProviderNumber");
                                    String customerEmail = jsonObject.getString("customerEmail");
                                    String customerName = jsonObject.getString("customerName");
                                    String customerNumber = jsonObject.getString("customerNumber");

                                    jobsListModel.add(new Job(job_id, date, state, bookingType, time, serviceProviderEmail, serviceProviderName, serviceProviderNumber, customerEmail, customerName, customerNumber));
                                }
                                jobsAdapter = new InProgressJobsAdapter(context, jobsListModel);
                                view.setAdapter(jobsAdapter);

                                pd.dismiss();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            pd.dismiss();
                        }
                    }
                });
    }
}
