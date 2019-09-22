package com.sport.x.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import com.mikhaellopez.circularimageview.CircularImageView;

import com.sport.x.Models.ConversationMessage;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;


public class ConversationMessagesAdapter extends RecyclerView.Adapter {

    private Context context;

    SharedPref SharedPref;
    ArrayList<ConversationMessage> messages = new ArrayList<ConversationMessage>();
    int role = 100;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private String bitmapTo64;

    public ConversationMessagesAdapter(Context context, ArrayList<ConversationMessage> messagesList) {
        this.context = context;
        this.messages = messagesList;
        SharedPref= new SharedPref(context);

    }


    //Get Total Messages
    @Override
    public int getItemCount() {
        return messages.size();
    }


    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ConversationMessage message = (ConversationMessage) messages.get(position);
        if (message.getConversationSenderEmail().equals(SharedPref.getEmail()))
//        if (message.getConversationSenderEmail().equals("ali"))
        {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }


    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ConversationMessage message = (ConversationMessage) messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageView profileImageSent;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
//            profileImageSent = (ImageView) itemView.findViewById(R.id.image_message_profile_sent);
        }

        void bind(ConversationMessage message) {
            messageText.setText(message.getConversationMessage()+"\n"+message.getTime());

            // Format the stored timestamp into a readable String using method.
            //timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
            timeText.setText(message.getConversationDate());


//            if(SharedPref.getEmail().equals(message.getCustomerEmail()))
//            {
//                Ion.with(context).load(message.getCustomerPicture().replace("\"","")).intoImageView(profileImageSent);
//            }
//            else if(SharedPref.getEmail().equals(message.getServiceProviderEmail()))
//            {
//                Ion.with(context).load(message.getServiceProviderPicture().replace("\"","")).intoImageView(profileImageSent);
//            }
//            else
//            {
//                profileImageSent.setImageResource(R.drawable.user);
//
//            }


        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        CircularImageView profileImage;
        Bitmap image;
        Bitmap bit2=null;
        Bitmap bit=null;
        Picasso picasso;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            profileImage = itemView.findViewById(R.id.image_message_profile);
        }
        void bind(ConversationMessage message) {
            messageText.setText(message.getConversationMessage()+"\n"+message.getTime());

            // Format the stored timestamp into a readable String using method.
            //timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
            timeText.setText(message.getConversationDate());

            if(SharedPref.getUserRole()==1)
            {
                nameText.setText(message.getCustomerName());
            }
            else
            {
                nameText.setText(message.getServiceProviderName());
            }

            if(SharedPref.getUserRole()==2)
            {
                Picasso.get()
                        .load(message.getServiceProviderPicture().replace("\"",""))
                        .placeholder(R.drawable.user)
                        .error(R.drawable.user)
                        .resize(52, 52)
                        .centerCrop()
                        .into(profileImage);
            }
            else if(SharedPref.getUserRole()==1)
            {
                Picasso.get()
                        .load(message.getCustomerPicture().replace("\"",""))
                        .placeholder(R.drawable.user)
                        .error(R.drawable.user)
                        .resize(52, 52)
                        .centerCrop()
                        .into(profileImage);
            }
            else
            {
                profileImage.setImageResource(R.drawable.user);
            }

        }

    }
    }
