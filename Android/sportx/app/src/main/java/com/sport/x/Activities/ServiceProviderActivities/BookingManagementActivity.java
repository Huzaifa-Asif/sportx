package com.sport.x.Activities.ServiceProviderActivities;

import android.content.Intent;
import android.support.design.widget.TabLayout;

import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentPagerAdapter;
import androidx.core.view.ViewPager;
import android.os.Bundle;

import com.sport.x.Activities.Menu.Menu;
import com.sport.x.Fragments.ProviderCompletedJobs;
import com.sport.x.Fragments.ProviderInProgressJobs;
import com.sport.x.Fragments.ProviderPendingJobs;
import com.sport.x.R;

public class BookingManagementActivity extends Menu {

    /**
     * The {@link androidx.core.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link androidx.core.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sp_booking_management);
        setTitle("Booking Management Service Provider");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
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
                    ProviderPendingJobs providerPendingJobs = new ProviderPendingJobs();
                    return providerPendingJobs;

                case 1:
                    ProviderInProgressJobs providerInProgressJobs = new ProviderInProgressJobs();
                    return providerInProgressJobs;

                case 2:
                    ProviderCompletedJobs providerCompletedJobs = new ProviderCompletedJobs();
                    return providerCompletedJobs;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
