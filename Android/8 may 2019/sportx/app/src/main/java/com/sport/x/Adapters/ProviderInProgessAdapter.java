package com.sport.x.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.x.Misc.Misc;
import com.sport.x.Models.Job;
import com.sport.x.R;

import java.util.ArrayList;

public class ProviderInProgessAdapter extends RecyclerView.Adapter<ProviderInProgessAdapter.ProviderInProgressViewHolder> {

    private ArrayList<Job> jobsListModel = new ArrayList<>();
    private Context context;
    Misc misc;

    public ProviderInProgessAdapter(Context context, ArrayList<Job> jobsListModel){
        this.context = context;
        this.jobsListModel = jobsListModel;
        misc = new Misc(context);
    }

    @NonNull
    @Override
    public ProviderInProgressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.in_progress_job_item, viewGroup, false);
        return new ProviderInProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderInProgressViewHolder providerInProgressViewHolder, int i) {
        providerInProgressViewHolder.setData(jobsListModel.get(i));
    }

    @Override
    public int getItemCount() {
        return jobsListModel.size();
    }

    public class ProviderInProgressViewHolder extends RecyclerView.ViewHolder {

        private TextView text_item;

        public ProviderInProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            text_item = itemView.findViewById(R.id.ip_text);
        }
        public void setData(Job job){
            text_item.setText("Your "  + job.getServiceName()+"'s"+ " Job is in progress " );
        }
    }
}
