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

import com.sport.x.activities.sharedActivities.InProgressBookingDetailsActivity;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Job;
import com.sport.x.R;

import java.util.ArrayList;

public class InProgressJobsAdapter extends RecyclerView.Adapter<InProgressJobsAdapter.InProgressJobsViewHolder> {

    private ArrayList<Job> jobsListModel = new ArrayList<>();
    private Context context;
    Misc misc;

    public InProgressJobsAdapter(Context context, ArrayList<Job> jobsListModel){
        this.context = context;
        this.jobsListModel = jobsListModel;
        misc = new Misc(context);
    }

    @NonNull
    @Override
    public InProgressJobsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.in_progress_job_item, viewGroup, false);
        return new InProgressJobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InProgressJobsViewHolder inProgressJobsViewHolder, int i) {
        inProgressJobsViewHolder.setData(jobsListModel.get(i));

    }

    @Override
    public int getItemCount() {
        return jobsListModel.size();
    }

    public class InProgressJobsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView text_item;
        private ImageView image_item;

        public InProgressJobsViewHolder(@NonNull View itemView) {
            super(itemView);

            text_item = itemView.findViewById(R.id.ip_text);
            image_item = itemView.findViewById(R.id.ip_image);

            itemView.setOnClickListener(this);
        }

        public void setData(Job job){
            text_item.setText(job.getServiceProviderName() + " Booking of: "  + job.getBookingType()+" is in progress");
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, InProgressBookingDetailsActivity.class);
            intent.putExtra("date", jobsListModel.get(getAdapterPosition()).getDate());
            intent.putExtra("time", jobsListModel.get(getAdapterPosition()).getTime());
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
