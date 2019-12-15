package com.sport.x.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Adapters.CompletedCustomerTournamentAdapter;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Tournament;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerTournamentCompleted extends Fragment {

    private Context context;
    Misc misc;
    SharedPref sharedPref;
    private ArrayList<Tournament> tournamentsListModel;
    CompletedCustomerTournamentAdapter tournamentAdapter;
    private RecyclerView view;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tournament_completed, container, false);

        context = getActivity();
        misc = new Misc(context);
        sharedPref = new SharedPref(context);

        tournamentsListModel = new ArrayList<>();
        tournamentAdapter = new CompletedCustomerTournamentAdapter(context, tournamentsListModel);

        textView = rootView.findViewById(R.id.no_ip);

        view = rootView.findViewById(R.id.completed_tournaments);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.setAdapter(tournamentAdapter);

        if(misc.isConnectedToInternet()) {
            pendingTournaments();
        }
        else{
            misc.showToast("No Internet Connection");
        }
        return rootView;
    }

    private void pendingTournaments() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please wait... ");
        pd.setCancelable(false);
        pd.show();
        Ion.with(context)
                .load(misc.ROOT_PATH+"tournament/get_tournament")
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
                                tournamentsListModel.clear();
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    String tournament_id = jsonObject.getString("_id");
                                    String state = jsonObject.getString("state");
                                    String tournament_name = jsonObject.getString("name");
                                    String no_of_teams = jsonObject.getString("teams");
                                    String winning_prize = jsonObject.getString("winningPrize");
                                    String entry_fee = jsonObject.getString("entryFee");
                                    String tournament_type = jsonObject.getString("tournamentType");
                                    String max_days = jsonObject.getString("maxDays");
                                    String service_provider_email = jsonObject.getString("serviceProviderEmail");
                                    String adder_email = jsonObject.getString("adderEmail");
                                    String start_date = jsonObject.getString("startDate");
                                    String start_time = jsonObject.getString("startTime");
                                    String date = jsonObject.getString("date");

                                    if(state.equals("completed"))
                                    {
                                        tournamentsListModel.add(new Tournament(tournament_id, state, tournament_name, no_of_teams, winning_prize, entry_fee, tournament_type, max_days, service_provider_email, adder_email, start_date,start_time, date));
                                    }

                                }
                                tournamentAdapter = new CompletedCustomerTournamentAdapter(context, tournamentsListModel);
                                view.setAdapter(tournamentAdapter);

                                pd.dismiss();
                            } catch (JSONException e1)
                            {
                                e1.printStackTrace();
                            }
                            pd.dismiss();
                        }
                    }
                });
    }
}
