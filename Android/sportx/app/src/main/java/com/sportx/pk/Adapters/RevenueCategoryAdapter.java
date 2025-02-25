package com.sportx.pk.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.Ion;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.RevenueCategory;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class RevenueCategoryAdapter extends RecyclerView.Adapter<RevenueCategoryAdapter.RevenueViewHolder> {


    private ArrayList<RevenueCategory> categories = new ArrayList<>();
    private Context context;
    Misc misc;
    SharedPref sharedPref;
    ImageButton delete;
    public RevenueCategoryAdapter(Context context, ArrayList<RevenueCategory> categories){
        this.context = context;
        this.categories = categories;
        misc = new Misc(context);

    }
    @NonNull
    @Override
    public RevenueCategoryAdapter.RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_revenue_category, viewGroup, false);
        return new RevenueCategoryAdapter.RevenueViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RevenueCategoryAdapter.RevenueViewHolder RevenueViewHolder, int i) {
        RevenueViewHolder.setData(categories.get(i));

    }
    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class RevenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView category;


        public RevenueViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            delete=itemView.findViewById(R.id.delete);

            delete.setOnClickListener(this);

        }

        public void setData(final RevenueCategory RevenueCategory)
        {
            category.setText(RevenueCategory.getName());
            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    callDeleteRevenueCategoryWebservice(true,RevenueCategory.getRevenueCategoryId(),RevenueCategory);
                }
            });

        }

        public void callDeleteRevenueCategoryWebservice(boolean isShowDialog,String id,RevenueCategory RevenueCategory) {

            final RevenueCategory deletedRevenueCategory=RevenueCategory;

            Ion.with(context)
                    .load("DELETE", misc.ROOT_PATH + "revenuecategory/delete_revenueCategory/" + id)
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
                                JSONObject jsonObjectRevenueDeleted = new JSONObject(result.getResult());
                                Boolean status = jsonObjectRevenueDeleted.getBoolean("status");
                                if(status) {
                                    categories.remove(deletedRevenueCategory);
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
//            intent.putExtra("conversationId", Revenues.get(getAdapterPosition()).getRevenueId());
//            context.startActivity(intent);
//            ((Activity) context).finish();
        }

    }


}
