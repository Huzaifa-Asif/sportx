package com.sport.x.Activities.ServiceProviderActivities;

import android.os.Bundle;

import com.sport.x.Activities.Menu.Menu;
import com.sport.x.R;

public class AddBookingSettingsActivity extends Menu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sp_add_booking_settings);
        setTitle("Service Provider Booking");
    }

}
