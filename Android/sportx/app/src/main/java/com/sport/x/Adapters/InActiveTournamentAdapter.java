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

import com.sport.x.CustomerPendingJobDetailsActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Job;
import com.sport.x.Models.Tournament;
import com.sport.x.R;

import java.util.ArrayList;

public class InActiveTournamentAdapter extends RecyclerView.Adapter<InActiveTournamentAdapter.InActiveTournamentViewHolder>{


    private ArrayList<Tournament> tournamentsListModel = new ArrayList<>();
    private Context context;
    Misc misc;

    public InActiveTournamentAdapter(Context context, ArrayList<Tournament> tournamentsListModel){
        this.context = context;
        this.tournamentsListModel = tournamentsListModel;
        misc = new Misc(context);
    }

    @NonNull
    @Override
    public InActiveTournamentAdapter.InActiveTournamentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.inactive_tournament_item, viewGroup, false);
        return new InActiveTournamentAdapter.InActiveTournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InActiveTournamentViewHolder inActiveTournamentViewHolder, int i) {
        inActiveTournamentViewHolder.setData(tournamentsListModel.get(i));

    }

    @Override
    public int getItemCount() {
        return tournamentsListModel.size();
    }

    public class InActiveTournamentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView text_item, date_item, type;
        private ImageView image_item;

        public InActiveTournamentViewHolder(@NonNull View itemView) {
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
//            Intent intent = new Intent(context, CustomerPendingJobDetailsActivity.class);
//            intent.putExtra("date", tournamentsListModel.get(getAdapterPosition()).getDate());
//            intent.putExtra("time", tournamentsListModel.get(getAdapterPosition()).getTime());
//            intent.putExtra("job_id", tournamentsListModel.get(getAdapterPosition()).getJobId());
//            intent.putExtra("state", tournamentsListModel.get(getAdapterPosition()).getState());
//            intent.putExtra("bookingType", tournamentsListModel.get(getAdapterPosition()).getBookingType());
//            intent.putExtra("serviceProviderEmail", tournamentsListModel.get(getAdapterPosition()).getServiceProviderEmail());
//            intent.putExtra("serviceProviderName", tournamentsListModel.get(getAdapterPosition()).getServiceProviderName());
//            intent.putExtra("serviceProviderNumber", tournamentsListModel.get(getAdapterPosition()).getServiceProviderNumber());
//            intent.putExtra("customerEmail", tournamentsListModel.get(getAdapterPosition()).getCustomerEmail());
//            intent.putExtra("customerName", tournamentsListModel.get(getAdapterPosition()).getCustomerName());
//            intent.putExtra("customerNumber", tournamentsListModel.get(getAdapterPosition()).getCustomerNumber());
//            context.startActivity(intent);
//            ((Activity) context).finish();
        }

    }
}

