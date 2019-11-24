package com.sport.x.Activities.ServiceProviderActivities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.core.widget.DrawerLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.sport.x.Activities.Menu.Menu;
import com.sport.x.Misc.Misc;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends Menu
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {


    private GoogleMap mMap;
    private Marker myMarker;
    private double current_latitude, current_longitude;
    private TextView txt1, txt2;
    private CircularImageView img1;
    Misc misc;
    SharedPref sharedPref;
    private String password;
    private double latitude1, longitude1;
    int i;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CALL_PHONE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sp_home);

        setTitle("Home");

        sharedPref = new SharedPref(this);
        misc = new Misc(this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        misc.saveCurrentToken();

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        syncMap();
    }

    private void syncMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.service_map);
        mapFragment.getMapAsync(HomeActivity.this);
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if(misc.isConnectedToInternet()) {
            fetchUserProfile();
        }
        else{
            misc.showToast("No Internet Connection");
        }
    }

    private void fetchUserProfile(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();

        String email = sharedPref.getEmail();
        Ion.with(this)
                .load(misc.ROOT_PATH+"serviceprovider/search/serviceProviderByEmail/"+email)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null) {
                            pd.dismiss();
                            misc.showToast("Internet Connection Problem");
                            return;
                        }
                        else{
                            try {
                                pd.dismiss();

                                JSONObject jsonObject = new JSONObject(result.getResult());



                                    JSONObject locat = jsonObject.getJSONObject("location");

                                    current_latitude = locat.getDouble("lat");
                                    current_longitude = locat.getDouble("long");
                                    Log.wtf("Lat:",""+current_latitude);
                                    Log.wtf("Lat:",""+current_longitude);

                                    String user_image = jsonObject.getString("picture_profile");


                                LatLng service = new LatLng(current_latitude, current_longitude);
                                mMap.setMinZoomPreference(15);
                                myMarker = mMap.addMarker(new MarkerOptions().position(service).title("Service Location"));

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(service));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }
}
