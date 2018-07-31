package com.baking.thebaking.recipedeetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.thebaking.R;
import com.baking.thebaking.models.StepsItem;
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
import butterknife.Unbinder;

/**
 * Created by Safa Amin on 7/22/2018.
 */

public class RecipeStepDetailsFragment extends Fragment {

    public static String SELECTED_RECIPE_STEP_PARAM = "SELECTED_RECIPE_STEP";

    @BindView(R.id.playerView_recipe_step)
    SimpleExoPlayerView mPlayerView;

    private Unbinder unbinder;
    private StepsItem step;
    private SimpleExoPlayer mExoPlayer;

    public static RecipeStepDetailsFragment getInstance(StepsItem step) {
        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_RECIPE_STEP_PARAM, step);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_details, parent, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null) {
            step = args.getParcelable(SELECTED_RECIPE_STEP_PARAM);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initViews();
    }

    private void initViews() {
        if (step.getVideoURL() != null && !step.getVideoURL().equals("")) {
            initializePlayer(step.getVideoURL());
        }
    }


    /**
     * This method create an instance of the ExoPlayer and set the ExoPlayer.EventListener
     * to this fragment then prepare the MediaSource.
     *
     * @param videoURL the remote URL from baking json file
     */
    private void initializePlayer(String videoURL) {
        if (mExoPlayer == null) {
            Uri mediaUri = Uri.parse(videoURL);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        releasePlayer();
    }
}
