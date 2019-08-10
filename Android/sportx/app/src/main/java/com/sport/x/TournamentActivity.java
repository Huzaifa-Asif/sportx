package com.sport.x;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.sport.x.Fragments.TournamentCompleted;
import com.sport.x.Fragments.TournamentInProgress;
import com.sport.x.Fragments.TournamentInActive;
import com.sport.x.ServiceProviderActivities.ServiceHomeActivity;
import com.sport.x.SharedPref.SharedPref;

public class TournamentActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
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
        setContentView(R.layout.activity_tournament);
        setTitle("Tournament Management");

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Booking Management");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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
                    TournamentInActive tournamentInActive = new TournamentInActive();
                    return tournamentInActive;
                case 1:
                    TournamentInProgress tournamentInProgress = new TournamentInProgress();
                    return tournamentInProgress;
                case 2:
                    TournamentCompleted tournamentCompleted = new TournamentCompleted();
                    return tournamentCompleted;
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
            Intent intent = new Intent(this, ServiceHomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, AllServiceActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
