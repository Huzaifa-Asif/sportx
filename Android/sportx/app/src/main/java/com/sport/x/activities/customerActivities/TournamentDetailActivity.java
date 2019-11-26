package com.sport.x.activities.customerActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.activities.menu.Menu;
import com.sport.x.Adapters.CustomerTournamentTeamsAdapter;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.TournamentTeam;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TournamentDetailActivity extends Menu implements View.OnClickListener {


    private TextView _name,_service_provider, _type, _no_of_teams, _entry_fee, _no_of_days,_start_date, _winning_prize, _team_text, full_team;
    private String t_state, tournament_state, team_state, tournament_id,state, name,service_provider, type, entry_fee, no_of_days,start_date,start_time, winning_prize;
    private Button  tournament_state_, cancel_tournament, team_register, create_fixtures;
    FloatingActionButton add_team;
    private EditText team_name, team_contact;

    private String no_of_teams;

    private int total_teams;

    JSONArray players;

    SharedPref sharedPref;
    Misc misc;
    private boolean show = false;
    ArrayList<TournamentTeam> teams = new ArrayList<TournamentTeam>();
    private RecyclerView teamRecycler;
    private CustomerTournamentTeamsAdapter teamAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_c_tournament_detail);
        setTitle("Tournament Details");

        this.context = context;
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
        full_team = findViewById(R.id.fullTeams);

//        create_fixtures = findViewById(R.id.create_fixtures);
//        create_fixtures.setOnClickListener(this);


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

        teamAdapter = new CustomerTournamentTeamsAdapter(this, teams);
        teamRecycler.setLayoutManager(new LinearLayoutManager(this));
        teamRecycler.setAdapter(teamAdapter);


        if(no_of_teams.equals("8 Teams"))
        {
            total_teams=8;

        }
        if(no_of_teams.equals("16 Teams"))
        {
            total_teams=16;

        }
        if(no_of_teams.equals("24 Teams"))
        {
            total_teams=24;

        }
        if(no_of_teams.equals("32 Teams"))
        {
            total_teams=32;

        }
        // method call to fetch teams
        callTeamWebservice(true);

    }


    @Override
    public void onBackPressed() {

            Intent intent = new Intent(this, com.sport.x.activities.customerActivities.HomeActivity.class);
            startActivity(intent);
            finish();

    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == add_team.getId()) {
//            addTeam();
//        }
//        if(v.getId() == tournament_state_.getId()){
//            stateTournament();
//        }
//        if(v.getId() == cancel_tournament.getId()){
//            cancelTournament();
//        }
//        if(v.getId() == create_fixtures.getId()){
//            createFixtures();
//        }
    }

//    public void addTeam()
//    {
//
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.add_team_popup);
//
//        team_name = dialog.findViewById(R.id.team_name);
//        team_contact = dialog.findViewById(R.id.team_contact);
//        team_register = dialog.findViewById(R.id.team_register);
//
//        team_register.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("name", team_name.getText().toString());
//                jsonObject.addProperty("teamContact", team_contact.getText().toString());
//                jsonObject.addProperty("adderEmail", sharedPref.getEmail());
//                jsonObject.addProperty("tournamentId", tournament_id);
//                jsonObject.addProperty("state", team_state);
//
//                addTeamDataToServer(jsonObject);
//                dialog.dismiss();
//
//            }
//        });
//
//
//        dialog.show();
//    }

//    public void addTeamDataToServer(JsonObject jsonObject){
//        Ion.with(this)
//                .load(misc.ROOT_PATH+"team/add_team")
//                .setJsonObjectBody(jsonObject)
//                .asString()
//                .withResponse()
//                .setCallback(new FutureCallback<Response<String>>() {
//                    @Override
//                    public void onCompleted(Exception e, Response<String> result) {
//                        if (e != null) {
//                            misc.showToast("Please check your connection");
//                            return;
//                        }
//
//
//                        try{
//                            JSONObject jsonObject1 = new JSONObject(result.getResult());
//
//                            Boolean status = jsonObject1.getBoolean("status");
//
//
//                            if (!status) {
//                                misc.showToast("Sorry Try Again");
//                                return;
//                            }
//                            else if (status) {
//                                String team_id = jsonObject1.getString("_id");
//                                misc.showToast("Team Registered");
//                                teams.add(new TournamentTeam(team_state, team_name.getText().toString(), players,team_id,tournament_id,sharedPref.getEmail(),team_contact.getText().toString()));
//                                teamAdapter.notifyDataSetChanged();
//
//                                if(total_teams==teams.size())
//                                {
//                                    add_team.hide();
//                                    full_team.setVisibility(View.VISIBLE);
//
//                                }
//                            }
//
//                        }
//                        catch (JSONException e1) {
//                            e1.printStackTrace();
//                        }
//
//
//                    }
//                });
//    }





    public void callTeamWebservice(boolean isShowDialog)
    {

        if(teams.size()==0) {

        }
        final int teamSize = teams.size();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "team/get_team_by_tournament/" + tournament_id)
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
//                                JSONArray players = new JSONArray(jsonObjectTeam.getString("players"));

//

//                                Log.d( "TeamPlayers: ",TeamPlayers.getString(0));

                                teams.add(new TournamentTeam(state, name, players,teamId, tournamentId,  adderEmail, teamContact));

                            }

                            teamAdapter.notifyDataSetChanged();


                            if(total_teams==teams.size())
                            {
                                add_team.hide();
                                full_team.setVisibility(View.VISIBLE);

                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }

                });
    }





}