package com.sport.x.Adapters;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.sport.x.Models.Revenue;
import com.koushikdutta.ion.Ion;
import com.sport.x.Misc.Misc;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.revenueViewHolder> {


    private ArrayList<Revenue> revenues = new ArrayList<>();
    private Context context;
    Misc misc;
    SharedPref sharedPref;
    Button details;
    public RevenueAdapter(Context context, ArrayList<Revenue> revenues){
        this.context = context;
        this.revenues = revenues;
        misc = new Misc(context);

    }
    @NonNull
    @Override
    public RevenueAdapter.revenueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_revenue, viewGroup, false);
        return new RevenueAdapter.revenueViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RevenueAdapter.revenueViewHolder revenueViewHolder, int i) {
        revenueViewHolder.setData(revenues.get(i));

    }
    @Override
    public int getItemCount() {
        return revenues.size();
    }


    public class revenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView category,amount,date;


        public revenueViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            details=itemView.findViewById(R.id.details);

            details.setOnClickListener(this);

        }

        public void setData(final Revenue revenue)
        {
            final Revenue revenueDetails=revenue;
            category.setText(revenue.getrevenueCategory());
            amount.setText(""+revenue.getAmount());
            date.setText(revenue.getDate());
            details.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.revenue_details);
                    TextView revenueCategory = dialog.findViewById(R.id.category);
                    revenueCategory.setText(revenueDetails.getrevenueCategory());
                    TextView amount = dialog.findViewById(R.id.amount);
                    amount.setText(""+revenueDetails.getAmount());
                    TextView date = dialog.findViewById(R.id.date);
                    date.setText(revenueDetails.getDate());
                    TextView description = dialog.findViewById(R.id.description);
                    description.setText(revenueDetails.getDescription());
                    Button delete=dialog.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            callDeleterevenueWebservice(true,revenueDetails.getrevenueId(),revenue);
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }
            });

        }

        public void callDeleterevenueWebservice(boolean isShowDialog,String id,Revenue revenue) {

            final Revenue deletedrevenue=revenue;

            Ion.with(context)
                    .load("DELETE", misc.ROOT_PATH + "delete_revenue/" + id)
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
                                JSONObject jsonObjectrevenueDeleted = new JSONObject(result.getResult());
                                Boolean status = jsonObjectrevenueDeleted.getBoolean("status");
                                if(status) {
                                    revenues.remove(deletedrevenue);
                                    notifyDataSetChanged();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
//

                        }

                    });
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, MessageActivity.class);
//            intent.putExtra("conversationId", revenues.get(getAdapterPosition()).getrevenueId());
//            context.startActivity(intent);
//            ((Activity) context).finish();
        }

    }


}
