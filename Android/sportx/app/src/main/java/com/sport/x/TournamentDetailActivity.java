package com.sport.x;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.sport.x.Adapters.ExpenseAdapter;
import com.sport.x.Adapters.TournamentTeamsAdapter;

import com.sport.x.Misc.Misc;
import com.sport.x.Models.Expense;
import com.sport.x.Models.TournamentTeam;
import com.sport.x.ServiceProviderActivities.ServiceHomeActivity;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TournamentDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView _name,_service_provider, _type, _no_of_teams, _entry_fee, _no_of_days,_start_date, _winning_prize, _team_text;
    private String tournament_id,state, name,service_provider, type, no_of_teams, entry_fee, no_of_days,start_date,start_time, winning_prize;
    private Button add_team, accept_tournament, cancel_tournament;

    SharedPref sharedPref;
    Misc misc;
    private boolean show = false;
    ArrayList<TournamentTeam> teams = new ArrayList<TournamentTeam>();
    private RecyclerView teamRecycler;
    private TournamentTeamsAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_detail);
        setTitle("Tournament");

        misc = new Misc(this);
        sharedPref = new SharedPref(this);


        _name = findViewById(R.id.c_name);
        _service_provider = findViewById(R.id.c_service_provider);
        _type = findViewById(R.id.c_type);
        _no_of_teams = findViewById(R.id.c_teams);
        _entry_fee= findViewById(R.id.c_entry);
        _no_of_days = findViewById(R.id.c_days);
        _start_date = findViewById(R.id.c_start_date);
        _winning_prize = findViewById(R.id.c_winning_prize);
        _team_text = findViewById(R.id.no_team_text);

        add_team = findViewById(R.id.add_team);
        add_team.setOnClickListener(this);

        accept_tournament = findViewById(R.id.accept_tournament);
        accept_tournament.setOnClickListener(this);

        cancel_tournament = findViewById(R.id.cancel_tournament);
        cancel_tournament.setOnClickListener(this);


        Intent intent = getIntent();

        tournament_id=intent.getStringExtra("tournament_id");
        state=intent.getStringExtra("state");
        name=intent.getStringExtra("name");
        no_of_teams=intent.getStringExtra("teams");
        winning_prize=intent.getStringExtra("winningPrize");
        entry_fee = intent.getStringExtra("entryFee");
        type = intent.getStringExtra("tournamentType");
        no_of_days = intent.getStringExtra("maxDays");
        start_date = intent.getStringExtra("startDate");
        start_time = intent.getStringExtra("startTime");
        service_provider = intent.getStringExtra("serviceProviderEmail");


        _name.setText("Name: " + name);
        _service_provider.setText("Service Provider: " + service_provider);
        _type.setText("Type: " + type);
        _no_of_teams.setText("No of Teams: " + no_of_teams);
        _no_of_days.setText("No of Days: " + no_of_days);
        _entry_fee.setText("Entry Fee: " + entry_fee);
        _start_date.setText("Start Time: " + start_date + " "+ start_time);
        _winning_prize.setText("Prize: " + winning_prize);

        teamRecycler =  findViewById(R.id.reyclerview_team_list);

        teamAdapter = new TournamentTeamsAdapter(this, teams);
        teamRecycler.setLayoutManager(new LinearLayoutManager(this));
        teamRecycler.setAdapter(teamAdapter);


        // method call to fetch teams
        callTeamWebservice(true);

    }


    @Override
    public void onBackPressed() {
        if(sharedPref.getUserRole()==1){
            Intent intent = new Intent(this, ServiceHomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, AllServiceActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == add_team.getId()) {
            addTeam();
        }
        if(v.getId() == accept_tournament.getId()){
            acceptTournament();
        }
        if(v.getId() == cancel_tournament.getId()){
            cancelTournament();
        }
    }

    public void addTeam()
    {

    }

    public void acceptTournament()
    {

    }

    public void cancelTournament()
    {

    }



    public void callTeamWebservice(boolean isShowDialog)
    {

        if(teams.size()==0) {

        }
        final int teamSize = teams.size();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "get_team_by_tournament/" + tournament_id)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null) {

                            misc.showToast("Please check your connection");

                            return;
                        }

                        try {

                            JSONArray jsonArray = new JSONArray(result.getResult());
                            if (jsonArray.length() < 1) {
                                _team_text.setVisibility(View.VISIBLE);
//                                misc.showToast("No teams Found");
                                return;
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObjectTeam = (JSONObject) jsonArray.get(i);
                                String teamId = jsonObjectTeam.getString("_id");
                                String tournamentId = jsonObjectTeam.getString("tournamentId");
                                String name = jsonObjectTeam.getString("name");
                                String state = jsonObjectTeam.getString("state");
                                String teamContact = jsonObjectTeam.getString("teamContact");
                                String adderEmail = jsonObjectTeam.getString("adderEmail");
//                              String players = jsonObjectTeam.getString("name");
                                JSONArray players = new JSONArray(jsonObjectTeam.getString("players"));

//

//                                Log.d( "TeamPlayers: ",TeamPlayers.getString(0));

                                teams.add(new TournamentTeam(state, name, players, tournamentId, teamId, adderEmail, teamContact));

                            }

                            teamAdapter.notifyDataSetChanged();


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });
    }



    private void Book(){
//        Intent intent = new Intent(this, BookingActivity.class);
//        intent.putExtra("service_provider_name",service_provider_name);
//        intent.putExtra("service_name", service_name);
//        intent.putExtra("service_provider_email", service_provider_email);
//        intent.putExtra("service_provider_phone_number", service_provider_phone_number);
//        intent.putExtra("service_provider_address", service_provider_address);
//        intent.putExtra("service_provider_latitude", service_provider_latitude);
//        intent.putExtra("service_provider_longitude", service_provider_longitude);
//        startActivity(intent);
    }



}