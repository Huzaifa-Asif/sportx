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

import com.sport.x.Activities.SharedActivites.PendingBookingDetailsActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Job;
import com.sport.x.R;

import java.util.ArrayList;

public class ProviderPendingAdapter extends RecyclerView.Adapter<ProviderPendingAdapter.ProviderPendingViewHolder> {

    private ArrayList<Job> jobsListModel = new ArrayList<>();
    private Context context;
    Misc misc;

    public ProviderPendingAdapter(Context context, ArrayList<Job> jobsListModel) {
        this.context = context;
        this.jobsListModel = jobsListModel;
        misc = new Misc(context);
    }

    @NonNull
    @Override
    public ProviderPendingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.in_progress_job_item, viewGroup, false);
        return new ProviderPendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderPendingAdapter.ProviderPendingViewHolder ProviderPendingViewHolder, int i) {
        ProviderPendingViewHolder.setData(jobsListModel.get(i));
    }
    @Override
    public int getItemCount() {
        return jobsListModel.size();
    }

    public class ProviderPendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView text_item;
        private ImageView image_item;

        public ProviderPendingViewHolder(@NonNull View itemView) {
            super(itemView);
            text_item = itemView.findViewById(R.id.ip_text);
            image_item = itemView.findViewById(R.id.ip_image);

            itemView.setOnClickListener(this);
        }

        public void setData(Job job) {
            text_item.setText(job.getCustomerName() + "'s" + " Request for : " + job.getBookingType() + " Booking, On  : " + job.getDate() + " - " + job.getTime());
        }


        public void onClick(View v) {
            Intent intent = new Intent(context, PendingBookingDetailsActivity.class);
            intent.putExtra("date", jobsListModel.get(getAdapterPosition()).getDate());
            intent.putExtra("job_id", jobsListModel.get(getAdapterPosition()).getJobId());
            intent.putExtra("state", jobsListModel.get(getAdapterPosition()).getState());
            intent.putExtra("time", jobsListModel.get(getAdapterPosition()).getTime());
            intent.putExtra("bookingType", jobsListModel.get(getAdapterPosition()).getBookingType());
            intent.putExtra("serviceProviderEmail", jobsListModel.get(getAdapterPosition()).getServiceProviderEmail());
            intent.putExtra("serviceProviderName", jobsListModel.get(getAdapterPosition()).getServiceProviderName());
            intent.putExtra("serviceProviderNumber", jobsListModel.get(getAdapterPosition()).getServiceProviderNumber());
            intent.putExtra("customerEmail", jobsListModel.get(getAdapterPosition()).getCustomerEmail());
            intent.putExtra("customerName", jobsListModel.get(getAdapterPosition()).getCustomerName());
            intent.putExtra("customerNumber", jobsListModel.get(getAdapterPosition()).getCustomerNumber());
            context.startActivity(intent);
            ((Activity) context).finish();
        }

    }
}



