package com.sportx.pk.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.TournamentTeam;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;




public class TournamentTeamsAdapter extends RecyclerView.Adapter<TournamentTeamsAdapter.TournamentTeamsViewHolder> {


    private ArrayList<TournamentTeam> teams = new ArrayList<>();
    private Context context;
    Misc misc;
    SharedPref sharedPref;
    ImageButton details;
    TournamentTeam teamUpdate;
    String teamStateVal;
    public TournamentTeamsAdapter(Context context, ArrayList<TournamentTeam> teams){
        this.context = context;
        this.teams = teams;
        misc = new Misc(context);

    }
    @NonNull
    @Override
    public TournamentTeamsAdapter.TournamentTeamsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_team, viewGroup, false);
        return new TournamentTeamsAdapter.TournamentTeamsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TournamentTeamsAdapter.TournamentTeamsViewHolder TournamentTeamsViewHolder, int i) {
        TournamentTeamsViewHolder.setData(teams.get(i));

    }
    @Override
    public int getItemCount() {
        return teams.size();
    }


    public class TournamentTeamsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView teamName_, teamState_, teamContact_;
        private String teamName, teamState;


        public TournamentTeamsViewHolder(@NonNull View itemView) {
            super(itemView);

            teamName_ = itemView.findViewById(R.id.com_name);
            teamState_ = itemView.findViewById(R.id.com_state);
            teamContact_ = itemView.findViewById(R.id.com_contact);

            itemView.setOnClickListener(this);
        }

        public void setData(final TournamentTeam team) {
            final TournamentTeam teamDetails = team;
            teamName_.setText(team.getTournamentTeamName());
            teamState_.setText(team.getTournamentTeamState());
            teamContact_.setText(team.getTournamentTeamContact());
        }

//        TournamentTeam team
        public void callDeleteExpenseWebservice(boolean isShowDialog,String id, Integer pos) {

            final Integer pos_id=pos;

            Ion.with(context)
                    .load("DELETE", misc.ROOT_PATH + "team/delete_team/" + id)
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
                                JSONObject jsonObjectTeamDeleted = new JSONObject(result.getResult());
                                Boolean status = jsonObjectTeamDeleted.getBoolean("status");
                                if(status) {
                                    teams.remove(teams.get(pos_id));

                                    notifyDataSetChanged();
                                    misc.showToast("Team Deleted");
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }


                        }

                    });
        }

        public void callUpdateStateWebservice(boolean isShowDialog,String id, Integer pos, String teamState) {

            final Integer pos_id=pos;
//            final String state_val=state;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("state", teamState);

            Ion.with(context)
                    .load("PATCH", misc.ROOT_PATH + "team/update_team/" + id)
                    .setJsonObjectBody(jsonObject)
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
                                JSONObject jsonObjectTeamUpdated = new JSONObject(result.getResult());
                                Boolean status = jsonObjectTeamUpdated.getBoolean("status");
                                String state = jsonObjectTeamUpdated.getString("state");
                                if(status) {

                                    teamUpdate=teams.get(pos_id);
                                    teamUpdate.setState(state);
                                    teams.set(pos_id, teamUpdate);
                                    notifyDataSetChanged();
                                    misc.showToast("Team State Updated");
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }


                        }

                    });
        }

        @Override
        public void onClick(View v) {

            Log.d("adapter position: ",""+getAdapterPosition());
                    final int adapterPosition = getAdapterPosition();


                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup_sh_team_details);
                    TextView teamName = dialog.findViewById(R.id.teamName);
                    teamName.setText(teams.get(getAdapterPosition()).getTournamentTeamName());
                    final TextView teamContact = dialog.findViewById(R.id.teamContact);
                    teamContact.setText(teams.get(getAdapterPosition()).getTournamentTeamContact());
                    TextView teamState = dialog.findViewById(R.id.teamState);
                    teamState.setText(teams.get(getAdapterPosition()).getTournamentTeamState());

                    String teamStateValue = teams.get(getAdapterPosition()).getTournamentTeamState();


                    teamContact.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + teams.get(getAdapterPosition()).getTournamentTeamContact()));
                        context.startActivity(intent);
                        }
                    });


                    Button delete=dialog.findViewById(R.id.deleteTeam);
                    delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                        {

                            callDeleteExpenseWebservice(true,teams.get(getAdapterPosition()).getTournamentTeamId(), adapterPosition);
                            dialog.dismiss();

                        }
                    });


                     Button stateButton=dialog.findViewById(R.id.updateState);

                    if(teamStateValue.equals("pending") || teamStateValue.equals("blocked"))
                    {
                        stateButton.setText("approve");
                        teamStateVal="approved";

                    }
                    else if (teamStateValue.equals("approved"))
                    {
                        stateButton.setText("block");
                        teamStateVal="blocked";
                    }
                    stateButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                        {
                        callUpdateStateWebservice(true,teams.get(getAdapterPosition()).getTournamentTeamId(), adapterPosition, teamStateVal);
                        dialog.dismiss();

                        }
                    });
                    dialog.show();
        }

    }

    }



