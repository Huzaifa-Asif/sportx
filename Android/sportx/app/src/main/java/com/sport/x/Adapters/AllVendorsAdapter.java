package com.sport.x.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;


import com.sport.x.Models.Service_Provider;
import com.sport.x.R;
import com.sport.x.activities.customerActivities.ServiceProviderDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class AllVendorsAdapter extends RecyclerView.Adapter<AllVendorsAdapter.VendorViewHolder> {

    private Context context;
    private ArrayList<Service_Provider> vendorsListModel;
    private ArrayList<Service_Provider> tempVendorsListModel = new ArrayList<>();


    public AllVendorsAdapter(Context context, ArrayList<Service_Provider> vendorsListModel){
        this.context = context;
        this.vendorsListModel = vendorsListModel;
        this.tempVendorsListModel.addAll(vendorsListModel);
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        vendorsListModel.clear();
        if (charText.length() == 0) {
            vendorsListModel.addAll(tempVendorsListModel);
        } else {
            for (Service_Provider af : tempVendorsListModel) {
                if (af.getServiceProviderName().toLowerCase().contains(charText)) {
                    vendorsListModel.add(af);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void setTemp(ArrayList<Service_Provider> serviceListModel) {
        this.tempVendorsListModel = new ArrayList<>();
        this.tempVendorsListModel.addAll(serviceListModel);
    }

    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_service_provider, viewGroup, false);
        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorViewHolder vendorViewHolder, int i) {
        vendorViewHolder.setData(vendorsListModel.get(i));
    }

    @Override
    public int getItemCount() {
        return vendorsListModel.size();
    }

    public class VendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircularImageView sp_image;
        private TextView sp_name,sp_address;

        public VendorViewHolder(@NonNull View itemView) {
            super(itemView);

            sp_image = itemView.findViewById(R.id.sp_image);
            sp_name = itemView.findViewById(R.id.sp_name);
            sp_address = itemView.findViewById(R.id.sp_address);

            itemView.setOnClickListener(this);
        }


        public void setData(Service_Provider sp){
            sp_name.setText(sp.getServiceProviderName());
            sp_address.setText(sp.getServiceProviderAddress());
            if(sp.getServiceProviderPicture().isEmpty()){
                sp_image.setImageResource(R.drawable.usersicon);
            }
            else{
                Picasso.get()
                        .load(sp.getServiceProviderPicture())
                        .into(sp_image);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ServiceProviderDetailsActivity.class);
            intent.putExtra("service_provider_name", vendorsListModel.get(getAdapterPosition()).getServiceProviderName());
            intent.putExtra("service_name", vendorsListModel.get(getAdapterPosition()).getServiceProviderCategory());
            intent.putExtra("service_provider_email", vendorsListModel.get(getAdapterPosition()).getServiceProviderEmail());
            intent.putExtra("service_provider_phone_number", vendorsListModel.get(getAdapterPosition()).getServiceProviderContact());
            intent.putExtra("service_provider_address", vendorsListModel.get(getAdapterPosition()).getServiceProviderAddress());
            intent.putExtra("service_provider_latitude", vendorsListModel.get(getAdapterPosition()).getUserLat());
            intent.putExtra("service_provider_longitude", vendorsListModel.get(getAdapterPosition()).getUserLon());
            intent.putExtra("calling","searchByName");
//            intent.putExtra("status", vendorsListModel.get(getAdapterPosition()).getCustomerStatus());
//            intent.putExtra("id", vendorsListModel.get(getAdapterPosition()).getCustomerId());

            context.startActivity(intent);
            ((Activity) context).finish();

        }
    }
}
