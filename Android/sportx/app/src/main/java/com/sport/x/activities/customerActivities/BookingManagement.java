package com.sport.x.activities.customerActivities;

import android.content.Intent;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.sport.x.Fragments.CustomerCompletedJobs;
import com.sport.x.Fragments.CustomerInProgressJobs;
import com.sport.x.Fragments.CustomerPendingJobs;
import com.sport.x.activities.menu.Menu;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

public class BookingManagement extends Menu {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_c_booking_management);
        setTitle("Booking Management");


        int position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("position");
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(position);
        Log.wtf("position",""+position);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        sharedPref = new SharedPref(this);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    CustomerPendingJobs customerPendingJobs = new CustomerPendingJobs();
                    return customerPendingJobs;
                case 1:
                    CustomerInProgressJobs customerInProgressJobs = new CustomerInProgressJobs();
                    return customerInProgressJobs;
                case 2:
                    CustomerCompletedJobs customerCompletedJobs = new CustomerCompletedJobs();
                    return customerCompletedJobs;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }
    }

    @Override
    public void onBackPressed() {
        if(sharedPref.getUserRole().equals("1")){
            Intent intent = new Intent(this, com.sport.x.activities.serviceProviderActivities.HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
