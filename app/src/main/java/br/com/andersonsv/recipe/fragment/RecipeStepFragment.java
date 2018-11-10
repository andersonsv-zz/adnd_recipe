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
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

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
    private int stepCount;


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

    @Nullable
    @BindView(R.id.ivStep)
    ImageView mStepImage;

    @Nullable
    @BindView(R.id.btnPrevious)
    Button mPreviousButton;

    @Nullable
    @BindView(R.id.btnNext)
    Button mNextButton;

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
        checkButtons();

        if(step.getVideoURL() != null && !step.getVideoURL().isEmpty()){
            loadVideo(step.getVideoURL());
        }else{
            loadImage(step.getThumbnailURL());
        }

        return view;
    }

    private void loadImage(String imageUrl) {
        if(step.getThumbnailURL() != null
                && !step.getThumbnailURL().isEmpty()){

            if(imageUrl.contains(".mp4")){
                loadVideo(imageUrl);
            }else{
                mExPlayer.setVisibility(View.GONE);

                if(mStepImage != null)
                mStepImage.setVisibility(View.VISIBLE);

                Picasso.get()
                        .load(imageUrl)
                        .into(mStepImage);
            }
        }
    }

    private void checkButtons() {
        if(mPreviousButton != null){
            mPreviousButton.setVisibility(View.VISIBLE);

            if(step.getId() == 0){
                mPreviousButton.setVisibility(View.INVISIBLE);
            }
        }

        if(mNextButton != null) {
            mNextButton.setVisibility(View.VISIBLE);

            if (step.getId() == stepCount - 1) {
                mNextButton.setVisibility(View.INVISIBLE);
            }
        }

    }

    private void loadVideo(String videoUrl) {
        if(mPlayer == null){

            Context context = getContext() != null ? getContext() : getActivity();

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "mediaPlayerSample"), bandwidthMeter);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).setExtractorsFactory(extractorsFactory).createMediaSource(Uri.parse(videoUrl));

            mPlayer = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
            mPlayer.prepare(mediaSource);
            mExPlayer.setPlayer(mPlayer);

        }
    }

    private void checkUiNulls() {
        if(mStepNumber != null){
            mStepNumber.setText(String.format("%s",step.getId()));
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

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
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
