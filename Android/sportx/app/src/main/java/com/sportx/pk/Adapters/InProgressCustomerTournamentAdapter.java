package com.sportx.pk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sportx.pk.activities.customerActivities.TournamentDetailActivity;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.Tournament;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import java.util.ArrayList;


public class InProgressCustomerTournamentAdapter extends RecyclerView.Adapter<InProgressCustomerTournamentAdapter.InProgressTournamentViewHolder>{


    private ArrayList<Tournament> tournamentsListModel = new ArrayList<>();
    private Context context;
    Misc misc;
    SharedPref sharedPref;

    public InProgressCustomerTournamentAdapter(Context context, ArrayList<Tournament> tournamentsListModel){
        this.context = context;
        this.tournamentsListModel = tournamentsListModel;
        misc = new Misc(context);
    }

    @NonNull
    @Override
    public InProgressCustomerTournamentAdapter.InProgressTournamentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.inprogress_tournament_item, viewGroup, false);
        return new InProgressCustomerTournamentAdapter.InProgressTournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InProgressCustomerTournamentAdapter.InProgressTournamentViewHolder inProgressTournamentViewHolder, int i) {
        inProgressTournamentViewHolder.setData(tournamentsListModel.get(i));

    }

    @Override
    public int getItemCount() {
        return tournamentsListModel.size();
    }

    public class InProgressTournamentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView text_item, date_item, type;
        private ImageView image_item;

        public InProgressTournamentViewHolder(@NonNull View itemView) {
            super(itemView);

            text_item = itemView.findViewById(R.id.com_text);
            date_item = itemView.findViewById(R.id.com_date);
            type = itemView.findViewById(R.id.com_type);
            image_item = itemView.findViewById(R.id.com_image);

            itemView.setOnClickListener(this);
        }

        public void setData(Tournament tournament){
            text_item.setText(tournament.getTournamentName());
            date_item.setText(tournament.getTournamentRecordDate());
            type.setText(tournament.getTournamentType());
        }

        @Override
        public void onClick(View v) {

                Intent intent = new Intent(context, TournamentDetailActivity.class);
                intent.putExtra("tournament_id", tournamentsListModel.get(getAdapterPosition()).getTournamentId());
                intent.putExtra("state", tournamentsListModel.get(getAdapterPosition()).getTournamentState());
                intent.putExtra("name", tournamentsListModel.get(getAdapterPosition()).getTournamentName());
                intent.putExtra("teams", tournamentsListModel.get(getAdapterPosition()).getTournamentTeam());
                intent.putExtra("winningPrize", tournamentsListModel.get(getAdapterPosition()).getTournamentWinningPrize());
                intent.putExtra("entryFee", tournamentsListModel.get(getAdapterPosition()).getTournamentEntryFee());
                intent.putExtra("tournamentType", tournamentsListModel.get(getAdapterPosition()).getTournamentType());
                intent.putExtra("maxDays", tournamentsListModel.get(getAdapterPosition()).getTournamentMaxDays());
                intent.putExtra("serviceProviderEmail", tournamentsListModel.get(getAdapterPosition()).getTournamentServiceProviderEmail());
                intent.putExtra("adderEmail", tournamentsListModel.get(getAdapterPosition()).getTournamentAdderEmail());
                intent.putExtra("startDate", tournamentsListModel.get(getAdapterPosition()).getTournamentStartDate());
                intent.putExtra("startTime", tournamentsListModel.get(getAdapterPosition()).getTournamentStartTime());
                intent.putExtra("date", tournamentsListModel.get(getAdapterPosition()).getTournamentRecordDate());
                context.startActivity(intent);
                ((Activity) context).finish();



        }

    }
}

