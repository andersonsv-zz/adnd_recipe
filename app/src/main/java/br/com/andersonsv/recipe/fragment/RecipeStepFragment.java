package br.com.andersonsv.recipe.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Step;
import br.com.andersonsv.recipe.ui.StepListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP;

public class RecipeStepFragment extends Fragment implements PlaybackPreparer, PlayerControlView.VisibilityListener {

    private int index;
    private Step step;

    @Nullable
    @BindView(R.id.tvStepName)
    TextView mStepName;

    @Nullable
    @BindView(R.id.tvStepNumber)
    TextView mStepNumber;

    @Nullable
    @BindView(R.id.tvDescription)
    TextView mDescription;

    @BindView(R.id.exPlayer)
    PlayerView mExPlayer;

    private Unbinder unbinder;
    SimpleExoPlayer mPlayer;

    //controller
    private StepListener stepListener;

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

        checkUiNulls();

        if(step.getVideoURL() != null && !step.getVideoURL().isEmpty()){
            loadVideo();
        }else{
            mExPlayer.setVisibility(View.GONE);

        }

        return view;
    }

    private void loadVideo() {
        if(mPlayer == null){
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "mediaPlayerSample"), bandwidthMeter);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).setExtractorsFactory(extractorsFactory).createMediaSource(Uri.parse(step.getVideoURL()));

            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            mPlayer.prepare(mediaSource);
            mExPlayer.setPlayer(mPlayer);

        }
    }

    private void checkUiNulls() {
        if(mStepNumber != null){
            mStepNumber.setText(step.getId().toString());
        }

        if(mStepName != null){
            mStepName.setText(step.getShortDescription());
        }

        if(mDescription != null){
            mDescription.setText(step.getDescription());
        }
    }

    @Optional
    @OnClick(R.id.btnPrevious)
    public void previous() {
        stepListener.onPrevious();
    }

    @Optional
    @OnClick(R.id.btnNext)
    public void next() {
        stepListener.onNext();
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
        }
    }

    @Override
    public void preparePlayback() {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepListener = (StepListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " activity should implements interface StepListener.");
        }
    }
}
