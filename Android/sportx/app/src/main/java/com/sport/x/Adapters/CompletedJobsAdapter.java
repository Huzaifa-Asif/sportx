package com.sport.x.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sport.x.Activities.SharedActivites.CompletedBookingDetailsActivity;
import com.sport.x.Models.Job;
import com.sport.x.R;

import java.util.ArrayList;

public class CompletedJobsAdapter extends RecyclerView.Adapter<CompletedJobsAdapter.CompletedJobsViewHolder> {

    private Context context;
    private ArrayList<Job> jobsListModel;

    public CompletedJobsAdapter(Context context, ArrayList<Job> jobsListModel){

        this.context = context;
        this.jobsListModel = jobsListModel;

    }

    @NonNull
    @Override
    public CompletedJobsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.completed_job_item, viewGroup, false);
        return new CompletedJobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedJobsViewHolder completedJobsViewHolder, int i) {
        completedJobsViewHolder.setData(jobsListModel.get(i));
    }

    @Override
    public int getItemCount() {
        return jobsListModel.size();
    }

    public class CompletedJobsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView comp_image;
        private TextView comp_text, comp_service;

        public CompletedJobsViewHolder(@NonNull View itemView) {
            super(itemView);

            comp_image = itemView.findViewById(R.id.com_image);
            comp_text = itemView.findViewById(R.id.com_text);
            comp_service = itemView.findViewById(R.id.com_service);

            itemView.setOnClickListener(this);
        }

        public void setData(Job job){
            comp_text.setText(job.getServiceProviderName());
            comp_service.setText(job.getBookingType()+ " Booking"+ " - On: "+job.getDate() + " " + job.getTime());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, CompletedBookingDetailsActivity.class);
            intent.putExtra("date", jobsListModel.get(getAdapterPosition()).getDate());
            intent.putExtra("job_id", jobsListModel.get(getAdapterPosition()).getJobId());
            intent.putExtra("state", jobsListModel.get(getAdapterPosition()).getState());
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
