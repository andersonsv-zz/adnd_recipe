package br.com.andersonsv.recipe.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP;

public class RecipeStepFragment extends Fragment implements PlaybackPreparer, PlayerControlView.VisibilityListener {

    private int index;
    private Step step;

    @BindView(R.id.tvStepName)
    TextView mStepName;

    @BindView(R.id.tvStepNumber)
    TextView mStepNumber;

    @BindView(R.id.tvDescription)
    TextView mDescription;

    @BindView(R.id.exPlayer)
    PlayerView mExPlayer;

    private Unbinder unbinder;
    SimpleExoPlayer mPlayer;

    public RecipeStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(EXTRA_STEP);
        }

        mStepNumber.setText(step.getStepNumber());
        mStepName.setText(step.getShortDescription());
        mDescription.setText(step.getDescription());

        if(step.getVideoURL() != null && !step.getVideoURL().isEmpty()){
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "mediaPlayerSample"), bandwidthMeter);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).setExtractorsFactory(extractorsFactory).createMediaSource(Uri.parse(step.getVideoURL()));

            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            mPlayer.prepare(mediaSource);
            mExPlayer.setPlayer(mPlayer);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_STEP, step);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }

        if(mPlayer != null){
            mPlayer.setPlayWhenReady(false);
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void preparePlayback() {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }
}
