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

//import com.dps.mydoctor.MyVdoctorApp;
//import com.dps.mydoctor.R;
//import com.dps.mydoctor.callbacks.OnItemClickListener;
//import com.dps.mydoctor.models.ConversationsModel;
//import com.dps.mydoctor.models.DirectoryModel;
//import com.dps.mydoctor.utils.ApiConstant;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sport.x.AdminActivities.CustomerDetailsActivity;
import com.sport.x.Models.Customer;
import com.sport.x.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.MyHolder> {



    private Context context;
    private OnItemClickListener listener;
    String type = "";
    ArrayList<ConversationsModel> conversationsModels = new ArrayList<ConversationsModel>();
    MyVdoctorApp myVdoctorApp;

    public ConversationsAdapter(Context context, MyVdoctorApp myVdoctorApp, String type, ArrayList<ConversationsModel> conversationsModels, OnItemClickListener listener) {
        this.context = context;
        this.myVdoctorApp = myVdoctorApp;
        this.type = type;
        this.listener = listener;
        this.conversationsModels = conversationsModels;

    }

     /*
    INITIALIZE VIEWHOLDER
     */

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_conversation, parent, false);
        return new MyHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /*
      BIND
       */
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.setData(conversationsModels.get(position));

    }

    /*
      TOTAL SPACECRAFTS NUM
       */
    @Override
    public int getItemCount() {
        return conversationsModels.size();
    }

    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_name, tv_disease;
        ImageView img_user;
        ConversationsModel conversationsModel = null;

        public void setData(ConversationsModel conversationsModel) {
            this.conversationsModel = conversationsModel;

            if (type.equals("1")) {
                tv_name.setText(conversationsModel.getPatient_full_name());
                myVdoctorApp.setUserImageFromURL(context, img_user, ApiConstant.PROFILE_PICTURE_PATH + conversationsModel.getPatient_profile_picture());

            } else {
                tv_name.setText(conversationsModel.getDoctor_full_name());
                myVdoctorApp.setUserImageFromURL(context, img_user, ApiConstant.PROFILE_PICTURE_PATH + conversationsModel.getDoctor_profile_picture());

            }
            if(!conversationsModel.getRequest_text().equals("null")) {
                tv_disease.setVisibility(View.VISIBLE);
                tv_disease.setText(conversationsModel.getRequest_text());
            }else{
                tv_disease.setVisibility(View.GONE);
            }
            //img_user.setText(directoryModel.getAddress());
        }

        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img_user = itemView.findViewById(R.id.img_user);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_disease = itemView.findViewById(R.id.tv_disease);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }

    }
}
