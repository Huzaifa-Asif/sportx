package com.sport.x.AdminActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.sport.x.Adapters.CustomerServiceAdapter;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Service;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllServiceProvidersActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    Misc misc;
    SharedPref sharedPref;
    private RecyclerView recyclerView;
    CustomerServiceAdapter customerServiceAdapter;
    private ArrayList<Service> serviceListModel;
    private SearchView searchService = null;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_service_providers);
        setTitle("All Services");

        context = this;

        misc = new Misc(this);
        sharedPref = new SharedPref(this);

        serviceListModel = new ArrayList<>();

        searchService = findViewById(R.id.sv_search);
        recyclerView = findViewById(R.id.customer_services);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        searchService.setOnQueryTextListener(this);
        searchService.setOnClickListener(this);

        if(misc.isConnectedToInternet()){
            getServices();
        }
        else{
            misc.showToast("No Internet Connection");
        }
    }

    public void getServices(){
        Ion.with(this)
                .load(misc.ROOT_PATH+"get_services")
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null) {
                            misc.showToast("Please check your connection");
                            return;
                        }
                        else{
                            try {
                                JSONArray jsonArray = new JSONArray(result.getResult());
                                serviceListModel.clear();
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    String service_id = jsonObject.getString("service_id");
                                    String service_name = jsonObject.getString("service_name");
                                    String service_image = jsonObject.getString("service_image").replace("\"", "");

                                    serviceListModel.add(new Service(service_id, service_name, service_image));
                                }
                                customerServiceAdapter = new CustomerServiceAdapter(context, serviceListModel);
                                customerServiceAdapter.setTemp(serviceListModel);
                                recyclerView.setAdapter(customerServiceAdapter);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == searchService.getId()) {
            searchService.onActionViewExpanded();
            searchService.setIconified(false);

        }
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        if (customerServiceAdapter != null) {
            customerServiceAdapter.filter(s);
        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (customerServiceAdapter != null) {
            customerServiceAdapter.filter(s);
        }
        return true;

    }
}
