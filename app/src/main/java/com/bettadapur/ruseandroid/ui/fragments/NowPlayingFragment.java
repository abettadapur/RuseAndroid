package com.bettadapur.ruseandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerNowPlayingComponent;
import com.bettadapur.ruseandroid.dagger.NowPlayingComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.model.Status;
import com.bettadapur.ruseandroid.net.RuseService;
import com.bettadapur.ruseandroid.presenter.NowPlayingPresenter;
import com.bettadapur.ruseandroid.ui.activities.MainActivity;
import com.bettadapur.ruseandroid.ui.controls.NowPlayingControlsView;
import com.bettadapur.ruseandroid.ui.controls.NowPlayingQueueView;
import com.bettadapur.ruseandroid.ui.controls.NowPlayingSummaryView;
import com.bettadapur.ruseandroid.ui.views.NowPlayingView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Alex on 8/7/2015.
 */
public class NowPlayingFragment extends MvpFragment<NowPlayingView, NowPlayingPresenter> implements NowPlayingView
{
    private NowPlayingComponent mComponent;
    private NowPlayingPresenter mPresenter;
    private NowPlayingSummaryView mSummaryView;
    private NowPlayingControlsView mControlsView;
    private NowPlayingQueueView mQueueView;

    private ImageView mBigArtView;

    private ViewGroup mContainer;

    @Bind(R.id.slideHeader)
    FrameLayout mHeaderContainer;

    @Bind(R.id.slideContent)
    FrameLayout mContentContainer;

    @Bind(R.id.slideFooter)
    FrameLayout mFooterContainer;


    Bus mBus;
    RuseService mRuseService;

    @Override
    protected void injectDependencies()
    {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().ruseModule(new RuseModule(getActivity())).build();

        mComponent = DaggerNowPlayingComponent.builder().applicationComponent(applicationComponent).build();
        mComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = container;
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_now_playing;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize views
        mControlsView = new NowPlayingControlsView(getActivity());
        mSummaryView = new NowPlayingSummaryView(getActivity(), this);
        mQueueView = new NowPlayingQueueView(getActivity());
        mBigArtView = new ImageView(getActivity());

        //add views to containers
        mHeaderContainer.addView(mSummaryView);
        mFooterContainer.addView(mControlsView);
        mContentContainer.addView(mBigArtView);

        ((MainActivity)getActivity()).setSlideView(mHeaderContainer);
    }

    @Override
    public NowPlayingPresenter createPresenter() {
        mPresenter =  mComponent.presenter();
        return mPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }


    @Override
    public void updateStatus(Status status) {
        getActivity().runOnUiThread(()-> {
            mControlsView.updateStatus(status);
            if(status.getCurrent() != null)
            {
                Picasso.with(getActivity()).load(status.getCurrent().getArtSrc()).fit().centerCrop().into(mBigArtView);
            }
            mSummaryView.updateStatus(status);
        });
    }

    @Override
    public void updateQueue(Song[] songs)
    {
        getActivity().runOnUiThread(() ->
                        mQueueView.updateQueue(songs)
        );
    }

    @Override
    public void setExpanded(boolean b) {
        mSummaryView.setExpanded(b);
    }

    @Override
    public void openQueue() {
        mContentContainer.removeAllViews();
        mContentContainer.addView(mQueueView);
    }

    @Override
    public void closeQueue()
    {
        mContentContainer.removeAllViews();
        mContentContainer.addView(mBigArtView);
    }
}
