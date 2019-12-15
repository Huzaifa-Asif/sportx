package com.sportx.pk.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sportx.pk.Models.Complain;
import com.sportx.pk.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintsViewHolder> {

    private Context context;
    private ArrayList<Complain> complainsListModel;

    public ComplaintsAdapter(Context context, ArrayList<Complain> complainsListModel){
        this.context = context;
        this.complainsListModel = complainsListModel;
    }

    @NonNull
    @Override
    public ComplaintsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.complaint_item, viewGroup, false);
        return new ComplaintsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintsViewHolder complaintsViewHolder, int i) {
        complaintsViewHolder.setData(complainsListModel.get(i));
    }

    @Override
    public int getItemCount() {
        return complainsListModel.size();
    }

    public class ComplaintsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView user_name, user_msg;
        private CircularImageView user_img;

        public ComplaintsViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.complaint_name);
            user_img = itemView.findViewById(R.id.complaint_image);
            user_msg = itemView.findViewById(R.id.complaint_msg);

            itemView.setOnClickListener(this);
        }

        public void setData(Complain complain) {
            user_name.setText(complain.getUserName());
            user_msg.setText(complain.getComplainMessage());

            if(complain.getUserImage().isEmpty()){
                user_img.setImageResource(R.drawable.usersicon);
            }
            else{
                Picasso.get()
                        .load(complain.getUserImage())
                        .into(user_img);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }
}
