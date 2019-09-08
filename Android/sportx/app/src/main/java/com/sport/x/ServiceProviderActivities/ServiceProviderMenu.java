package com.sport.x.ServiceProviderActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sport.x.AllServiceActivity;
import com.sport.x.CompareActivity;
import com.sport.x.ComplainActivity;
import com.sport.x.ConversationActivity;
import com.sport.x.CreateTournamentActivity;
import com.sport.x.HelpActivity;
import com.sport.x.JobHistoryActivity;
import com.sport.x.LoginActivity;
import com.sport.x.PasswordUpdate;
import com.sport.x.ProfileActivity;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;
import com.sport.x.TournamentActivity;

public class ServiceProviderMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

SharedPref sharedPref;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    protected FrameLayout contentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref=new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_provider_menu);
        toolbar =  findViewById(R.id.toolbar);
        contentFrameLayout=findViewById(R.id.content_frame);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        Log.wtf("drawer",""+drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
    protected void inflateView(final int layoutResID)
    {
        getLayoutInflater().inflate(layoutResID, contentFrameLayout);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.service_profile) {
            Intent profile = new Intent(this, ServiceProfileActivity.class);
            startActivity(profile);
            finish();
        }
        else if (id == R.id.service_update_password) {
            Intent profile = new Intent(this, SerivceUpdatePasswordActivity.class);
            startActivity(profile);
            finish();
        }
        else if (id == R.id.conversation) {
            Intent conversation = new Intent(this, ConversationActivity.class);
            startActivity(conversation);
            finish();
        }
        else if (id == R.id.accounts) {
            Intent accounts = new Intent(this, AccountsActivity.class);
            startActivity(accounts);
            finish();
        }
        else if (id == R.id.tournament) {
            Intent conversation = new Intent(this, TournamentActivity.class);
            startActivity(conversation);
            finish();
        }
        else if (id == R.id.createtournament) {
            Intent conversation = new Intent(this, CreateTournamentActivity.class);
            startActivity(conversation);
            finish();
        }
        else if (id == R.id.service_home) {
            Intent home = new Intent(this, ServiceHomeActivity.class);
            startActivity(home);
            finish();
        } else if (id == R.id.service_jobs) {
            Intent providers = new Intent(this, ProviderJobsActivity.class);
            startActivity(providers);
            finish();
        }else if (id == R.id.service_update) {
            Intent update = new Intent(this, UpdateServicesActivity.class);
            startActivity(update);
            finish();
        } else if (id == R.id.service_help) {
            Intent help = new Intent(this, HelpActivity.class);
            help.putExtra("provider", "yes");
            startActivity(help);
            finish();
            finish();
        } else if (id == R.id.logout) {
            sharedPref.clearSession();
            Intent logout = new Intent(this, LoginActivity.class);
            startActivity(logout);
            finish();
        }
        else if (id == R.id.up_password) {
            Intent updatePassword = new Intent(this, SerivceUpdatePasswordActivity.class);
            startActivity(updatePassword);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
