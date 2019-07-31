package com.sport.x.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.dps.mydoctor.MyVdoctorApp;
//import com.dps.mydoctor.R;
//import com.dps.mydoctor.activities.sharedActivities.FullScreenImageActivity;
//import com.dps.mydoctor.callbacks.OnItemClickListener;
//import com.dps.mydoctor.models.ConversationsModel;
//import com.dps.mydoctor.models.MessagesModel;
//import com.dps.mydoctor.utils.ApiConstant;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.sport.x.AdminActivities.CustomerDetailsActivity;
import com.sport.x.Models.Customer;
import com.sport.x.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ConversationMessagesAdapter extends RecyclerView.Adapter<ConversationMessagesAdapter.MyHolder> {

    private Context context;
    private OnItemClickListener listener;

    ArrayList<MessagesModel> messagesModels = new ArrayList<MessagesModel>();
    MyVdoctorApp myVdoctorApp;
    String userId;

    public MessageAdapter(Context context,MyVdoctorApp myVdoctorApp,String userId, ArrayList<MessagesModel> messagesModels, OnItemClickListener listener) {
        this.context = context;
        this.myVdoctorApp = myVdoctorApp;
        this.userId = userId;
        this.listener = listener;
        this.messagesModels = messagesModels;

    }

     /*
    INITIALIZE VIEWHOLDER
     */

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_message, parent, false);
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
        //  Log.e("size",messagesModels.size()+"");
        holder.setData(messagesModels.get(position),messagesModels.get(position).getSender(),position);

    }

    /*
      TOTAL SPACECRAFTS NUM
       */
    @Override
    public int getItemCount() {
        return messagesModels.size();
    }

    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_message_gray,tv_message_blue;
        ImageView img_gray,img_blue;
        MessagesModel messagesModel = null;

        public void setData(MessagesModel messagesModel,String sender,int pos) {
            this.messagesModel= messagesModel;
            if(messagesModel.getMessage_type().equals("text")) {
                if (sender.equals(userId)) {
                    tv_message_gray.setText(messagesModel.getMessage());
                    tv_message_gray.setVisibility(View.VISIBLE);
                    tv_message_blue.setVisibility(View.GONE);
                    img_gray.setVisibility(View.GONE);
                    img_blue.setVisibility(View.GONE);
                } else {
                    tv_message_blue.setVisibility(View.VISIBLE);
                    tv_message_gray.setVisibility(View.GONE);
                    tv_message_blue.setText(messagesModel.getMessage());
                    img_gray.setVisibility(View.GONE);
                    img_blue.setVisibility(View.GONE);
                }
            }else {
                if (sender.equals(userId)) {
                    myVdoctorApp.setImageFromURL(context,img_gray,ApiConstant.MESSAGE_ATTACHMENT_PATH+messagesModel.getAttachment());
                    tv_message_gray.setVisibility(View.GONE);
                    tv_message_blue.setVisibility(View.GONE);
                    img_gray.setVisibility(View.VISIBLE);
                    img_blue.setVisibility(View.GONE);
                    img_gray.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(context, FullScreenImageActivity.class);
                            intent.putExtra("img_url",ApiConstant.MESSAGE_ATTACHMENT_PATH+messagesModel.getAttachment());
                            intent.putExtra("username","Attachment");
                            context.startActivity(intent);
                        }
                    });
                } else {
                    myVdoctorApp.setImageFromURL(context,img_blue,ApiConstant.MESSAGE_ATTACHMENT_PATH+messagesModel.getAttachment());
                    tv_message_blue.setVisibility(View.GONE);
                    tv_message_gray.setVisibility(View.GONE);
                    img_gray.setVisibility(View.GONE);
                    img_blue.setVisibility(View.VISIBLE);
                    img_blue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(context, FullScreenImageActivity.class);
                            intent.putExtra("img_url",ApiConstant.MESSAGE_ATTACHMENT_PATH+messagesModel.getAttachment());
                            intent.putExtra("username","Attachment");
                            context.startActivity(intent);
                        }
                    });
                }
            }
            // Log.e("position",pos+"");
            //img_user.setText(directoryModel.getAddress());
        }

        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_message_gray =  itemView.findViewById(R.id.tv_message_gray);
            tv_message_blue =  itemView.findViewById(R.id.tv_message_blue);
            img_gray =  itemView.findViewById(R.id.img_gray);
            img_blue =  itemView.findViewById(R.id.img_blue);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }

    }
}
