package com.sport.x.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.x.Misc.Misc;
import com.sport.x.Models.BookingSlot;
import com.sport.x.R;

import java.util.ArrayList;

public class BookingSlotAdapter extends RecyclerView.Adapter<BookingSlotAdapter.MyViewHolder> {

    private ArrayList<BookingSlot> mModelList;
    Context context;
    Misc misc;

    public BookingSlotAdapter(Context context,ArrayList<BookingSlot> modelList) {
        mModelList = modelList;
        this.context=context;
        misc=new Misc(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_slot, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final BookingSlot model = mModelList.get(position);
        holder.textView.setText(model.getStart()+" to "+model.getEnd());
        if(model.isAvailable())
        {
            holder.availability.setText("Available");
        }
        else
        {
            holder.availability.setText("Booked");
        }
        holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        holder.cardSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!model.isAvailable())
                {
                    misc.showToast("Slot Already Reserved");
                    return;
                }

                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.GREEN : Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView,availability;
        private CardView cardSlot;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView =  itemView.findViewById(R.id.slot);
            availability=itemView.findViewById(R.id.status);
            cardSlot=itemView.findViewById(R.id.card_slot);
        }
    }
}
