package com.sportx.pk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.sportx.pk.activities.sharedActivities.MessageActivity;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.Conversation;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import java.util.ArrayList;

//import com.sport.x.Models.Job;

public class ConversationArchivedAdapter extends RecyclerView.Adapter<ConversationArchivedAdapter.ConversationActiveViewHolder> {

    private ArrayList<Conversation> conversationsListModel = new ArrayList<>();

    //private ArrayList<Job> jobsListModel = new ArrayList<>();
    private Context context;
    Misc misc;
    SharedPref sharedPref;

    public ConversationArchivedAdapter(Context context, ArrayList<Conversation> conversationsListModel){
        this.context = context;
        this.conversationsListModel = conversationsListModel;
        misc = new Misc(context);
    }

    @NonNull
    @Override
    public ConversationActiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.conversation_item, viewGroup, false);
        return new ConversationActiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationActiveViewHolder ConversationActiveViewHolder, int i) {
        ConversationActiveViewHolder.setData(conversationsListModel.get(i));

    }

    @Override
    public int getItemCount() {
        return conversationsListModel.size();
    }

    public class ConversationActiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView comp_name,comp_date;
        private ImageView comp_image;

        public ConversationActiveViewHolder(@NonNull View itemView) {
            super(itemView);

            comp_image = itemView.findViewById(R.id.com_image);
            comp_name = itemView.findViewById(R.id.com_name);
            comp_date = itemView.findViewById(R.id.com_date);

            itemView.setOnClickListener(this);
        }

        public void setData(Conversation Conversation){

            if((Conversation.getConversationUserRole())==1)
            {
                comp_name.setText(Conversation.getConversationCustomerName());
                comp_date.setText(Conversation.getConversationDate()+" "+Conversation.getTime());

                if((Conversation.getConversationCustomerPicture())!=null)
                {
                    Log.d("service provider",Conversation.getConversationCustomerPicture());
                    Ion.with(context.getApplicationContext()).load(Conversation.getConversationCustomerPicture().replace("\"","")).intoImageView(comp_image);
                }
                else
                {
                    comp_image.setImageResource(R.drawable.user);
                }



            }

            else if((Conversation.getConversationUserRole())==2)
            {
                comp_name.setText(Conversation.getConversationServiceProviderName());
                comp_date.setText(Conversation.getConversationDate());

                if((Conversation.getConversationServiceProviderPicture())!=null)
                {
                    Ion.with(context.getApplicationContext()).load(Conversation.getConversationServiceProviderPicture().replace("\"","")).intoImageView(comp_image);
                }
                else
                {
                    comp_image.setImageResource(R.drawable.user);
                }

            }

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("conversationId", conversationsListModel.get(getAdapterPosition()).getConversationId());
            context.startActivity(intent);
            ((Activity) context).finish();
        }

    }
}
