package com.example.givasneves.receitasculinarias.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.model.Recipe;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoPlayerActivity extends AppCompatActivity {

    @BindView(R.id.sepRecipeStep)
    SimpleExoPlayerView sepv;
    @BindView(R.id.tv_step_text)
    TextView tvStpeText;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.btnPrev)
    Button btnPrev;
    @BindView(R.id.ivThumbnail)
    ImageView ivThumbnail;

    private SimpleExoPlayer sep;
    private Step step;
    private Recipe recipe;
    private Bundle bundleData;
    private int currentStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bundleData = savedInstanceState;
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
        initializeExoPlayer();
        prepareNavigation();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void prepareNavigation() {
        step = getIntent().getParcelableExtra(getString(R.string.parceable_step));
        recipe = getIntent().getParcelableExtra("recipe");

        if (this.bundleData != null)
            currentStep = this.bundleData.getInt("current-step", 0);

        if (currentStep == 0) {
            currentStep = getCurrentIndex(step);
        } else {
           step = recipe.steps.get(currentStep);
        }

        buttonsVisibility();
    }

    private int getCurrentIndex(Step _step) {
        for (int i = 0; i < recipe.steps.size(); i++) {
            if(step.id == recipe.steps.get(i).id)
                return i;
        }
        return -1;
    }

    private void buttonsVisibility() {

        if(currentStep == 0)
            btnPrev.setVisibility(View.INVISIBLE);
        else
            btnPrev.setVisibility(View.VISIBLE);

        if(currentStep >= (recipe.steps.size() -1))
            btnNext.setVisibility(View.INVISIBLE);
        else
            btnNext.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.btnNext)
    void nextStep() {
        currentStep++;
        moveStep();
    }

    @OnClick(R.id.btnPrev)
    void prevStep() {
        currentStep--;
        moveStep();
    }

    private void moveStep() {
        if(this.bundleData != null)
            this.bundleData.clear();

        this.step = this.recipe.steps.get(currentStep);
        buttonsVisibility();
        preparePlayer();
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
        outState.putInt("current-step", currentStep);
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

        sep.stop();
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
            } else {
                sep.setPlayWhenReady(true);
            }
        } else {
            sepv.setVisibility(View.GONE);
        }

        if (!this.step.thumbnailURL.isEmpty()) {
            Picasso.with(this).load(this.step.thumbnailURL.toString()).into(ivThumbnail);
        } else {
            ivThumbnail.setVisibility(View.GONE);
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
