package com.sport.x.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sport.x.Misc.Misc;
import com.sport.x.Models.Tournament;
import com.sport.x.R;
import com.sport.x.TournamentDetailActivity;

import java.util.ArrayList;




public class InProgressTournamentAdapter extends RecyclerView.Adapter<InProgressTournamentAdapter.InProgressTournamentViewHolder>{


    private ArrayList<Tournament> tournamentsListModel = new ArrayList<>();
    private Context context;
    Misc misc;

    public InProgressTournamentAdapter(Context context, ArrayList<Tournament> tournamentsListModel){
        this.context = context;
        this.tournamentsListModel = tournamentsListModel;
        misc = new Misc(context);
    }

    @NonNull
    @Override
    public InProgressTournamentAdapter.InProgressTournamentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.inprogress_tournament_item, viewGroup, false);
        return new InProgressTournamentAdapter.InProgressTournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InProgressTournamentAdapter.InProgressTournamentViewHolder inProgressTournamentViewHolder, int i) {
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

