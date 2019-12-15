package com.sportx.pk.activities.customerActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sportx.pk.Adapters.AllVendorsAdapter;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.Service_Provider;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;
import com.sportx.pk.activities.menu.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchByNameActivity extends Menu implements View.OnClickListener, SearchView.OnQueryTextListener {
    Context context;
    Misc misc;
    SharedPref sharedPref;
    private RecyclerView recyclerView;
    private SearchView searchService = null;
    private SwipeRefreshLayout refresh;
    private ArrayList<Service_Provider> vendorList = new ArrayList<>();
    private AllVendorsAdapter spAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_c_home);
        setTitle("Book Services");
        context=this;
        misc = new Misc(this);
        sharedPref = new SharedPref(this);
        getServiceProviders();
        refresh = findViewById(R.id.swipe);
        refresh.setColorSchemeResources(R.color.colorPrimary);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        searchService = findViewById(R.id.sv_search);
        recyclerView = findViewById(R.id.customer_services);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        spAdapter=new AllVendorsAdapter(this,vendorList);
        recyclerView.setAdapter(spAdapter);


        searchService.setOnQueryTextListener(this);
        searchService.setOnClickListener(this);
    }

    private void refreshContent() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getServiceProviders();
                refresh.setRefreshing(false);
            }
        });
    }

    public void getServiceProviders(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Finding  Service Providers");
        pd.setCancelable(false);
        pd.show();
        Ion.with(this)
                .load(misc.ROOT_PATH+"serviceprovider/search/get_all_approved")
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
                                if(jsonArray.length() < 1) {
                                    misc.showToast("No Service Provider Found");
                                    pd.dismiss();
                                    return;
                                }
                                vendorList.clear();
                                for(int i = 0; i < jsonArray.length(); i++){



                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                    JSONObject locat  = jsonObject.getJSONObject("location");

                                    Double latitude1 = locat.getDouble("lat");
                                    Double longitude1 = locat.getDouble("long");

                                    String service_provider_name = jsonObject.getString("name");
                                    String email = jsonObject.getString("email");
                                    String address = jsonObject.getString("address");
                                    String picture_profile = jsonObject.getString("picture_profile");
                                    String contact = jsonObject.getString("contact");
                                    String password = jsonObject.getString("password");
                                    String category = jsonObject.getString("category");
                                    vendorList.add(new Service_Provider(service_provider_name, email, address, picture_profile, contact, password, category, latitude1, longitude1));

                                }
                                pd.dismiss();
                                spAdapter.setTemp(vendorList);
                                spAdapter.notifyDataSetChanged();

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
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
        if (spAdapter != null) {
            spAdapter.filter(s);
        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (spAdapter != null) {
            spAdapter.filter(s);
        }
        return true;

    }

    }
