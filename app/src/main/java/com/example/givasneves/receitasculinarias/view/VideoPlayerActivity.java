package com.example.givasneves.receitasculinarias.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerActivity extends AppCompatActivity {

    @BindView(R.id.sepRecipeStep)
    SimpleExoPlayerView sepv;
    @BindView(R.id.tv_step_text)
    TextView tvStpeText;

    private SimpleExoPlayer sep;
    private Step step;
    private Bundle bundleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
        initializeExoPlayer();

        step = getIntent().getParcelableExtra(getString(R.string.parceable_step));
        this.bundleData = savedInstanceState;
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT <=  Build.VERSION_CODES.LOLLIPOP) {
            preparePlayer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Build.VERSION.SDK_INT >  Build.VERSION_CODES.LOLLIPOP) {
            preparePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Build.VERSION.SDK_INT <=  Build.VERSION_CODES.LOLLIPOP) {
            sep.release();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Build.VERSION.SDK_INT >  Build.VERSION_CODES.LOLLIPOP) {
            sep.release();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(sep != null) {
            outState.putLong("player-position", sep.getCurrentPosition());
            outState.putBoolean("player-playing", sep.getPlayWhenReady());
        }
    }

    private void initializeExoPlayer() {
        if(sep == null) {
            sepv.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.exo_controls_play));
            TrackSelector defaultTrackSelector = new DefaultTrackSelector();
            LoadControl defaultLoadControl = new DefaultLoadControl();
            sep = ExoPlayerFactory.newSimpleInstance(this, defaultTrackSelector, defaultLoadControl);
            sepv.setPlayer(sep);
            sep.setPlayWhenReady(true);
        }
    }

    public void preparePlayer() {

        this.tvStpeText.setText(step.description);
        if (!this.step.videoURL.isEmpty()) {
            sepv.setVisibility(View.VISIBLE);
            String userAgent = Util.getUserAgent(this, "ReceitasCulinarias");
            Uri mediaUri = Uri.parse(step.videoURL);
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            sep.prepare(mediaSource);
            if(bundleData != null) {
                long playerPosition = bundleData.getLong("player-position", 0);
                boolean playWhenReady = bundleData.getBoolean("player-playing", false);

                sep.setPlayWhenReady(playWhenReady);

                if( playerPosition != 0) {
                    sep.seekTo(playerPosition);
                }
            }
        } else {
            sepv.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
