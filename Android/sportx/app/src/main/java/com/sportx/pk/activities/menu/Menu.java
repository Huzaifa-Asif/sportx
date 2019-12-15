package com.sportx.pk.activities.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.activities.customerActivities.SearchByNameActivity;
import com.sportx.pk.activities.serviceProviderActivities.AccountsActivity;
import com.sportx.pk.activities.customerActivities.HomeActivity;
import com.sportx.pk.activities.customerActivities.CompareActivity;
import com.sportx.pk.activities.serviceProviderActivities.EditBookingSettingActivity;
import com.sportx.pk.activities.serviceProviderActivities.StreamActivity;
import com.sportx.pk.activities.sharedActivities.ComplainActivity;
import com.sportx.pk.activities.sharedActivities.ConversationActivity;
import com.sportx.pk.activities.sharedActivities.HelpActivity;
import com.sportx.pk.activities.customerActivities.BookingManagementActivity;
import com.sportx.pk.activities.sharedActivities.LoginActivity;
import com.sportx.pk.activities.customerActivities.UpdatePasswordActivity;
import com.sportx.pk.activities.customerActivities.UpdateProfileActivity;
import com.sportx.pk.activities.customerActivities.TournamentActivity;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;
import com.sportx.pk.activities.sharedActivities.OngoingStreamsActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPref sharedPrefMenu;
    Misc miscMenu;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    protected FrameLayout contentFrameLayout;
    SwitchCompat switcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefMenu=new SharedPref(this);
        miscMenu=new Misc(this);
        super.onCreate(savedInstanceState);
        if(sharedPrefMenu.getUserRole()==1)
        {
            setContentView(R.layout.menu_service_provider);

        }
        else if(sharedPrefMenu.getUserRole()==2)
        {
            setContentView(R.layout.menu_customer);
        }

        toolbar =  findViewById(R.id.toolbar);
        contentFrameLayout=findViewById(R.id.content_frame);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(sharedPrefMenu.getUserRole()==1)
        {
            android.view.Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.switchToogleButton);
            View actionView = MenuItemCompat.getActionView(menuItem);

            switcher = (SwitchCompat) actionView.findViewById(R.id.drawer_switch);
            if(sharedPrefMenu.getSpState())
            {
                switcher.setChecked(true);
            }
            switcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(switcher.isChecked())
                    {
                        setStatusAsAway();
                        sharedPrefMenu.setSpState(true);
                    }
                    else
                    {
                        setStatusAsApproved();
                        sharedPrefMenu.setSpState(false);
                    }
                }
            });
        }

    }

    protected void inflateView(final int layoutResID)
    {
        getLayoutInflater().inflate(layoutResID, contentFrameLayout);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(sharedPrefMenu.getUserRole()==1)
        {
            if (id == R.id.service_profile) {
                Intent profile = new Intent(this, com.sportx.pk.activities.serviceProviderActivities.UpdateProfileActivity.class);
                startActivity(profile);
                finish();
            }
            else if (id == R.id.service_update_password) {
                Intent profile = new Intent(this, com.sportx.pk.activities.serviceProviderActivities.UpdatePasswordActivity.class);
                startActivity(profile);
                finish();
            }

            else if (id == R.id.conversation) {
                Intent conversation = new Intent(this, ConversationActivity.class);
                startActivity(conversation);
                finish();
            }
            else if (id == R.id.switchToogleButton) {
                switcher.setChecked(!switcher.isChecked());
                if(switcher.isChecked())
                {
                    setStatusAsAway();
                    sharedPrefMenu.setSpState(true);
                }
                else
                {
                    setStatusAsApproved();
                    sharedPrefMenu.setSpState(false);
                }

            }
            else if (id == R.id.accounts) {
                Intent accounts = new Intent(this, AccountsActivity.class);
                startActivity(accounts);
                finish();
            }
            else if (id == R.id.livestream) {
                Intent stream = new Intent(this, StreamActivity.class);
                startActivity(stream);
                finish();
            }
            else if (id == R.id.booking_settings) {
                Intent bookingSettings = new Intent(this, EditBookingSettingActivity.class);
                startActivity(bookingSettings);
                finish();
            }
            else if (id == R.id.tournament) {
                Intent conversation = new Intent(this, com.sportx.pk.activities.serviceProviderActivities.TournamentActivity.class);
                startActivity(conversation);
                finish();
            }
            else if (id == R.id.createtournament) {
                Intent conversation = new Intent(this, com.sportx.pk.activities.serviceProviderActivities.CreateTournamentActivity.class);
                startActivity(conversation);
                finish();
            }
            else if (id == R.id.service_home) {
                Intent home = new Intent(this, com.sportx.pk.activities.serviceProviderActivities.HomeActivity.class);
                startActivity(home);
                finish();
            }

            else if (id == R.id.service_jobs) {
                Intent providers = new Intent(this, com.sportx.pk.activities.serviceProviderActivities.BookingManagementActivity.class);
                startActivity(providers);
                finish();
            } else if (id == R.id.service_help) {
                Intent help = new Intent(this, HelpActivity.class);
                help.putExtra("provider", "yes");
                startActivity(help);
                finish();
                finish();
            } else if (id == R.id.logout) {
                sharedPrefMenu.clearSession();
                Intent logout = new Intent(this, LoginActivity.class);
                startActivity(logout);
                finish();
            }
            else if (id == R.id.up_password) {
                Intent updatePassword = new Intent(this, com.sportx.pk.activities.serviceProviderActivities.UpdatePasswordActivity.class);
                startActivity(updatePassword);
                finish();
            }
        }
        else if(sharedPrefMenu.getUserRole()==2)
        {
            if (id == R.id.customer_home) {
                Intent home = new Intent(this, HomeActivity.class);
                startActivity(home);
                finish();
            } else if (id == R.id.customer_profile) {
                Intent profile = new Intent(this, UpdateProfileActivity.class);
                startActivity(profile);
                finish();
            }
            else if (id == R.id.conversation) {
                Intent conversation = new Intent(this, ConversationActivity.class);
                startActivity(conversation);
                finish();
            }
            else if (id == R.id.search_by_name) {
                Intent name = new Intent(this, SearchByNameActivity.class);
                startActivity(name);
                finish();
            }
            else if (id == R.id.compare) {
                if(sharedPrefMenu.getCompareServiceProvider1()==null&&sharedPrefMenu.getCompareServiceProvider2()==null&&sharedPrefMenu.getCompareServiceProvider3()==null&&sharedPrefMenu.getCompareServiceProvider4()==null)
                {
                    miscMenu.showToast("Kindly Select At least 1 Service Provider to see Stats");
                }
                else
                {
                    Intent compare = new Intent(this, CompareActivity.class);
                    startActivity(compare);
                    finish();
                }

            }
            else if (id == R.id.tournament) {
                Intent tournament = new Intent(this, TournamentActivity.class);
                startActivity(tournament);
                finish();
            }
            else if (id == R.id.viewstream) {
                Intent view = new Intent(this, OngoingStreamsActivity.class);
                startActivity(view);
                finish();
            }
            else if (id == R.id.update_password) {
                Intent update = new Intent(this, UpdatePasswordActivity.class);
                startActivity(update);
                finish();
            }
            else if (id == R.id.customer_history) {
                Intent job = new Intent(this, BookingManagementActivity.class);
                startActivity(job);
                finish();
            } else if (id == R.id.customer_complaints) {
                Intent complain = new Intent(this, ComplainActivity.class);
                startActivity(complain);
                finish();
            } else if (id == R.id.customer_help) {
                Intent help = new Intent(this, HelpActivity.class);
                help.putExtra("provider", "no");
                startActivity(help);
                finish();
            } else if (id == R.id.customer_logout) {
                sharedPrefMenu.clearSession();
                Intent logout = new Intent(this, LoginActivity.class);
                startActivity(logout);
                finish();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
private void setStatusAsAway()
{
    final ProgressDialog pd = new ProgressDialog(this);
    pd.setMessage("Updating Status As Away");
    pd.setCancelable(false);
    pd.show();

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("state","away");



    Ion.with(this)
            .load("PATCH", miscMenu.ROOT_PATH+"serviceprovider/update_serviceProvider/"+sharedPrefMenu.getEmail())
            .setJsonObjectBody(jsonObject)
            .asString()
            .withResponse()
            .setCallback(new FutureCallback<Response<String>>() {
                @Override
                public void onCompleted(Exception e, Response<String> result) {
                    if (e != null) {
                        pd.dismiss();
                        miscMenu.showToast("Please check your connection");
                        pd.dismiss();
                        return;
                    }

                    try{
                        JSONObject jsonObject2 = new JSONObject(result.getResult());

                        Boolean status = jsonObject2.getBoolean("status");


                        if (!status) {
                            String Message = jsonObject2.getString("Message");
                            pd.dismiss();
                            miscMenu.showToast(Message);
                            return;
                        }
                        else if (status) {

                            pd.dismiss();
                            miscMenu.showToast("Status Updated Successfully");

                        }

                    }
                    catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }
            });

}
    private void setStatusAsApproved()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Updating Status As Away");
        pd.setCancelable(false);
        pd.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state","approved");



        Ion.with(this)
                .load("PATCH", miscMenu.ROOT_PATH+"serviceprovider/update_serviceProvider/"+sharedPrefMenu.getEmail())
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null) {
                            pd.dismiss();
                            miscMenu.showToast("Please check your connection");
                            pd.dismiss();
                            return;
                        }

                        try{
                            JSONObject jsonObject2 = new JSONObject(result.getResult());

                            Boolean status = jsonObject2.getBoolean("status");


                            if (!status) {
                                String Message = jsonObject2.getString("Message");
                                pd.dismiss();
                                miscMenu.showToast(Message);
                                return;
                            }
                            else if (status) {

                                pd.dismiss();
                                miscMenu.showToast("Status Updated Successfully");

                            }

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }
                });

    }

}
