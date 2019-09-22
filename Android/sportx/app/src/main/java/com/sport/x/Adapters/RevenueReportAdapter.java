package com.sport.x.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.x.Misc.Misc;
import com.sport.x.Models.RevenueReport;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import java.util.ArrayList;


public class RevenueReportAdapter extends RecyclerView.Adapter<RevenueReportAdapter.RevenueReportViewHolder> {


    private ArrayList<RevenueReport> revenues = new ArrayList<>();
    private Context context;
    Misc misc;
    public RevenueReportAdapter(Context context, ArrayList<RevenueReport> revenues){
        this.context = context;
        this.revenues = revenues;
        misc = new Misc(context);

    }
    @NonNull
    @Override
    public RevenueReportAdapter.RevenueReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_revenue_report, viewGroup, false);
        return new RevenueReportAdapter.RevenueReportViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RevenueReportAdapter.RevenueReportViewHolder RevenueReportViewHolder, int i) {
        RevenueReportViewHolder.setData(revenues.get(i));


    }
    @Override
    public int getItemCount() {
        return revenues.size();
    }


    public class RevenueReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView category,amount;


        public RevenueReportViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            amount=itemView.findViewById(R.id.amount);



        }

        public void setData(final RevenueReport RevenueReport)
        {
            final RevenueReport RevenueReport1=RevenueReport;
            category.setText(RevenueReport1.getName());
            amount.setText(""+RevenueReport1.getAmount());

        }

        

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, MessageActivity.class);
//            intent.putExtra("conversationId", Revenues.get(getAdapterPosition()).getRevenueId());
//            context.startActivity(intent);
//            ((Activity) context).finish();
        }

    }


}
