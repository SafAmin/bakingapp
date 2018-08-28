package com.baking.thebaking.recipedetails.recipestep;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baking.thebaking.R;
import com.baking.thebaking.models.StepsItem;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Safa Amin on 7/22/2018.
 */

public class RecipeStepFragment extends Fragment {

    public static String SELECTED_RECIPE_STEP_PARAM = "SELECTED_RECIPE_STEP";
    public static String RECIPE_DETAILS_STEPS_PARAM = "RECIPE_DETAILS_STEPS";
    public static String SAVED_SELECTED_RECIPE_STEP_PARAM = "SAVED_SELECTED_RECIPE_STEP";
    public static String SAVED_RECIPE_DETAILS_STEPS_PARAM = "SAVED_RECIPE_DETAILS_STEPS";

    @BindView(R.id.playerView_recipe_step)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.tv_recipe_step_description)
    TextView tvStepDescription;
    @BindView(R.id.rv_recipe_steps)
    RecyclerView rvRecipeSteps;
    @BindView(R.id.layout_recipe_steps_container)
    LinearLayout layoutRecipeSteps;

    private Bundle state;
    private Unbinder unbinder;
    private StepsItem step;
    private List<StepsItem> stepsList;
    private SimpleExoPlayer exoPlayer;
    private String recipeVideoUrl;

    public static RecipeStepFragment getInstance(StepsItem step, List<StepsItem> stepsList) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_RECIPE_STEP_PARAM, step);
        args.putParcelableArrayList(RECIPE_DETAILS_STEPS_PARAM, (ArrayList<? extends Parcelable>) stepsList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_details, parent, false);
        unbinder = ButterKnife.bind(this, view);

        state = savedInstanceState;
        getExtras();

        return view;
    }

    private void getExtras() {
        Bundle args = getArguments();
        if (args != null && state == null) {
            step = args.getParcelable(SELECTED_RECIPE_STEP_PARAM);
            stepsList = args.getParcelableArrayList(RECIPE_DETAILS_STEPS_PARAM);
            recipeVideoUrl = step.getVideoURL();
        } else {
            step = state.getParcelable(SAVED_SELECTED_RECIPE_STEP_PARAM);
            stepsList = state.getParcelableArrayList(SAVED_RECIPE_DETAILS_STEPS_PARAM);
            recipeVideoUrl = step.getVideoURL();
        }

        initializePlayer();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initViews();
    }

    private void initViews() {
        tvStepDescription.setText(step.getDescription());
        invalidateVideoPlayerView();
        invalidateRecipeStepsView();
    }

    private void invalidateRecipeStepsView() {
        RecyclerView.LayoutManager stepsLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        if (stepsList != null) {
            toggleRecipeStepsView(View.VISIBLE);
            rvRecipeSteps.setLayoutManager(stepsLayoutManager);
            rvRecipeSteps.setNestedScrollingEnabled(false);
            rvRecipeSteps.setAdapter(new RecipeStepsAdapter(stepsList,
                    new RecipeStepsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(StepsItem item) {
                            tvStepDescription.setText(item.getDescription());
                            if (item.getVideoURL() != null && !item.getVideoURL().equals("")) {
                                buildMediaSource(item.getVideoURL());
                            }
                        }
                    }));
        } else {
            toggleRecipeStepsView(View.GONE);
        }
    }

    private void invalidateVideoPlayerView() {
        if (recipeVideoUrl != null && !recipeVideoUrl.isEmpty()) {
            toggleVideoPlayerView(View.VISIBLE);
            initializePlayer();
        } else {
            toggleVideoPlayerView(View.GONE);
        }
    }

    private void toggleVideoPlayerView(int visible) {
        exoPlayerView.setVisibility(visible);
    }

    private void toggleRecipeStepsView(int visible) {
        layoutRecipeSteps.setVisibility(visible);
    }

    /**
     * This method create an instance of the ExoPlayer and set the ExoPlayer.EventListener
     * to this fragment then prepare the MediaSource.
     */
    private void initializePlayer() {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);
            MediaSource mediaSource = buildMediaSource(recipeVideoUrl);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            Player.EventListener eventListener = new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            };
            exoPlayer.addListener(eventListener);
        }
    }

    private MediaSource buildMediaSource(String videoURL) {
        Uri mediaUri = Uri.parse(videoURL);
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(mediaUri);
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        releasePlayer();
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(SAVED_SELECTED_RECIPE_STEP_PARAM, step);
        currentState.putParcelableArrayList(SAVED_RECIPE_DETAILS_STEPS_PARAM, (ArrayList<? extends Parcelable>) stepsList);
    }
}
