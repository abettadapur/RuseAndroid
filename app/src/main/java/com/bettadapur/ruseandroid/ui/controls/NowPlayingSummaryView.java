package com.bettadapur.ruseandroid.ui.controls;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.Status;
import com.bettadapur.ruseandroid.net.RuseService;
import com.bettadapur.ruseandroid.ui.fragments.NowPlayingFragment;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alex on 8/7/2015.
 */
public class NowPlayingSummaryView extends FrameLayout
{
    private Status mCurrentStatus;
    private Context mContext;
    private NowPlayingFragment mParent;

    private boolean mExpanded;
    private boolean mQueueOpened = false;

    @Bind(R.id.artView)
    ImageView mArtView;

    @Bind(R.id.titleView)
    TextView mTitleView;

    @Bind(R.id.artistView)
    TextView mArtistView;

    @Bind(R.id.playButton)
    ImageButton mPlayButton;

    @Inject
    RuseService ruseService;

    public NowPlayingSummaryView(Context context, NowPlayingFragment parent)
    {
        super(context);
        mContext = context;
        mParent = parent;
        injectDependencies();
        initializeViews();

    }

    private void injectDependencies()
    {
        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(mContext)).build();
        component.inject(this);
    }

    private void initializeViews()
    {
        inflate(mContext, R.layout.view_now_playing_summary, this);
        ButterKnife.bind(this);
        mPlayButton.setOnClickListener((v)->
        {
            if(mCurrentStatus!=null)
            {
                if(mCurrentStatus.isPlaying())
                    ruseService.pause();
                else
                    ruseService.resume();

            }
        });
    }

    public void updateStatus(Status s)
    {
        mCurrentStatus = s;

        if(mCurrentStatus.getCurrent() != null)
        {
            mTitleView.setText(mCurrentStatus.getCurrent().getTitle());
            mArtistView.setText(mCurrentStatus.getCurrent().getArtist());
            Picasso.with(mContext).load(mCurrentStatus.getCurrent().getArtSrc()).into(mArtView);
        }

        if(!mExpanded)
        {
            if(mCurrentStatus.isPlaying())
            {
                mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_pause)
                        .color(0x000000)
                        .actionBarSize());
            } else {
                mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_play)
                        .color(0x000000)
                        .actionBarSize());
            }

        }
    }

    public void setExpanded(boolean b)
    {
        mExpanded = b;
        if(mExpanded)
        {
            if(mQueueOpened)
            {
                mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_reorder)
                        .colorRes(R.color.accentColor)
                        .actionBarSize());

            }
            else {
                mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_reorder)
                        .color(0x000000)
                        .actionBarSize());
            }

            mPlayButton.setOnClickListener((v)->
            {
                if(!mQueueOpened)
                {
                    mParent.openQueue();
                    mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_reorder).colorRes(R.color.accentColor).actionBarSize());
                }
                else
                {
                    mParent.closeQueue();
                    mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_reorder).color(0x000000).actionBarSize());
                }
                mQueueOpened = !mQueueOpened;
            });

        }
        else
        {
            if (mCurrentStatus.isPlaying()) {
                mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_pause)
                        .color(0x000000)
                        .actionBarSize());
            } else {
                mPlayButton.setImageDrawable(new IconDrawable(mContext, Iconify.IconValue.fa_play)
                        .color(0x000000)
                        .actionBarSize());
            }

            mPlayButton.setOnClickListener(view ->
            {
                if (mCurrentStatus != null) {

                        if(mCurrentStatus.isPlaying())
                            ruseService.pause();
                        else
                            ruseService.resume();


                }
            });
        }
    }



}
