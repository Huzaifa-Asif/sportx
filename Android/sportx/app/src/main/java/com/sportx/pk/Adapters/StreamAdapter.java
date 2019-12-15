package com.sportx.pk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.ion.Ion;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.LiveStream;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;
import com.sportx.pk.activities.sharedActivities.ViewStreamActivity;

import java.util.ArrayList;

public class StreamAdapter extends RecyclerView.Adapter<StreamAdapter.StreamViewHolder> {


    private ArrayList<LiveStream> streams ;
    private Context context;
    Misc misc;
    SharedPref sharedPref;

    public StreamAdapter(Context context, ArrayList<LiveStream> streams){
        this.context = context;
        this.streams = streams;
        misc = new Misc(context);


    }
    @NonNull
    @Override
    public StreamAdapter.StreamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_stream, viewGroup, false);
        return new StreamAdapter.StreamViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull StreamAdapter.StreamViewHolder streamViewHolder, int i) {
        streamViewHolder.setData(streams.get(i));

    }
    @Override
    public int getItemCount() {
        return streams.size();
    }


    public class StreamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name,time;
        private ImageView picture;
        private CardView cardview;


        public StreamViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.sp_name);
            time=itemView.findViewById(R.id.start_time);
            picture=itemView.findViewById(R.id.picture);
            cardview=itemView.findViewById(R.id.card_stream);
        }

        public void setData(final LiveStream liveStream)
        {
            name.setText(liveStream.getServiceProvideName());
            time.setText("Streaming Since "+liveStream.getTime());
            if((liveStream.getServiceProviderPicture())==null)
            {
                picture.setImageResource(R.drawable.user);

            }
            else
            {
                Ion.with(context.getApplicationContext()).load(liveStream.getServiceProviderPicture().replace("\"","")).intoImageView(picture);
            }
            cardview.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i=new Intent(context, ViewStreamActivity.class);
                    i.putExtra("email",liveStream.getServiceProviderEmail());
                    context.startActivity(i);
                    ((Activity) context).finish();
                }
            });

        }



        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, MessageActivity.class);
//            intent.putExtra("conversationId", expenses.get(getAdapterPosition()).getExpenseId());
//            context.startActivity(intent);
//            ((Activity) context).finish();
        }

    }


}
