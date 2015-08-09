package com.bettadapur.ruseandroid.ui.controls;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.Status;
import com.bettadapur.ruseandroid.net.RuseService;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alex on 8/7/2015.
 */
public class NowPlayingControlsView extends FrameLayout
{
    private Context mContext;
    private Status mCurrentStatus;

    @Bind(R.id.prevButton)
    ImageButton mPrevButton;

    @Bind(R.id.playButton)
    ImageButton mPlayButton;

    @Bind(R.id.nextButton)
    ImageButton mNextButton;

    @Bind(R.id.songProgress)
    ProgressBar mSongProgress;

    @Bind(R.id.currentTimeView)
    TextView mCurrentTimeView;

    @Bind(R.id.totalTimeView)
    TextView mTotalTimeView;

    @Inject
    RuseService ruseService;

    public NowPlayingControlsView(Context context)
    {
        super(context);
        mContext = context;
        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(mContext)).build();
        component.inject(this);
        initializeViews();
    }

    private void initializeViews()
    {
        inflate(mContext, R.layout.view_now_playing_controls, this);
        ButterKnife.bind(this);

        mPrevButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_fast_backward).color(0x000000).actionBarSize());
        mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_play).color(0xFFFFFF).actionBarSize());
        mNextButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_fast_forward).color(0x000000).actionBarSize());
    }

    @OnClick(R.id.prevButton)
    public void onPrevClick(View view)
    {
        ruseService.prev();
    }

    @OnClick(R.id.playButton)
    public void onPlayClick(View view)
    {
        if(mCurrentStatus!=null)
        {
            if(mCurrentStatus.isPlaying())
                ruseService.pause();
            else
                ruseService.resume();
        }
    }

    @OnClick(R.id.nextButton)
    public void onNextClick(View view)
    {
        ruseService.next();
    }

    public void updateStatus(Status status)
    {
        mCurrentStatus = status;

        if(status.isPlaying()) {
            mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_pause)
                    .color(0xFFFFFF)
                    .actionBarSize());
        }
        else
        {
            mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_play)
                    .color(0xFFFFFF)
                    .actionBarSize());
        }

        mSongProgress.setMax(status.getLength());
        mSongProgress.setProgress(status.getTime());

        mCurrentTimeView.setText(String.format("%2d:%02d", status.getTime()/60, status.getTime()%60));
        mTotalTimeView.setText(String.format("%2d:%02d", status.getLength()/60, status.getLength()%60));
    }

}
