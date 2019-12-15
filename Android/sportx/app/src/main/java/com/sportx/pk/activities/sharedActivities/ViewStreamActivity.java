package com.sportx.pk.activities.sharedActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.sportx.pk.activities.menu.Menu;
import com.sportx.pk.R;

public class ViewStreamActivity extends Menu {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sh_view_stream);
        Intent i=getIntent();
        String email=i.getStringExtra("email");
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        //Create the player
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        PlayerView playerView = findViewById(R.id.simple_player);
        playerView.setPlayer(player);

        RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(rtmpDataSourceFactory)
                .createMediaSource(Uri.parse("rtmp://192.64.114.135/live/"+email));

        // Prepare the player with the source.
        player.prepare(videoSource);
        //auto start playing
        player.setPlayWhenReady(true);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, OngoingStreamsActivity.class);
        startActivity(intent);
        finish();
    }
}
