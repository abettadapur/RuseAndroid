package com.bettadapur.ruseandroid.ui.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.test.ApplicationTestCase;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.Artist;
import com.bettadapur.ruseandroid.net.RuseService;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.hannesdorfmann.mosby.MosbyFragment;
import com.melnykov.fab.FloatingActionButton;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Alex on 8/11/2015.
 */
public class ArtistDetailFragment extends MosbyFragment implements ObservableScrollViewCallbacks {

    @Bind(R.id.artView)
    ImageView mArtView;

    @Bind(R.id.scrollView)
    ObservableScrollView mScrollView;

    @Bind(R.id.overlay)
    View mOverlayView;

    @Bind(R.id.fab)
    FloatingActionButton mPlayButton;

    @Inject
    RuseService ruseService;

    @Inject
    Bus bus;

    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;


    private Artist mCurrentArtist;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_artist_view;
    }

    @Override
    protected void injectDependencies() {
        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(getActivity())).build();
        component.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height_artist);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);

        //set action bar size
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[] { android.R.attr.actionBarSize };
        int indexOfAttrActionSize = 0;
        TypedArray a = getActivity().obtainStyledAttributes(typedValue.data, textSizeAttr);
        mActionBarSize = a.getDimensionPixelSize(indexOfAttrActionSize, 0);
        a.recycle();

        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);

        mScrollView.setScrollViewCallbacks(this);

        ScrollUtils.addOnGlobalLayoutListener(mScrollView, () -> onScrollChanged(0, false, false));

        refreshViews();
    }

    private void refreshViews()
    {
        //set up views with artist information
        if(mArtView != null)
        {
            Picasso.with(getActivity()).load(mCurrentArtist.getArtSrc()).into(mArtView);
        }
    }

    public void updateArtist(Artist artist)
    {
        mCurrentArtist = artist;
    }

    @Override
    public void onScrollChanged(int y, boolean firstScroll, boolean dragging)
    {
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();

        mOverlayView.setTranslationY(ScrollUtils.getFloat(-y, minOverlayTransitionY, 0));
        mArtView.setTranslationY(ScrollUtils.getFloat(-y / 2, minOverlayTransitionY, 0));

        int maxFABTranslationY = mFlexibleSpaceImageHeight - mPlayButton.getHeight()/2;
        float fabTranslationY = ScrollUtils.getFloat(-y + mFlexibleSpaceImageHeight -mPlayButton.getHeight()/2, mActionBarSize - mPlayButton.getHeight()/2, maxFABTranslationY);

        mPlayButton.setTranslationX(mOverlayView.getWidth() - mFabMargin - mPlayButton.getWidth());
        mPlayButton.setTranslationY(fabTranslationY);

        if(fabTranslationY < mFlexibleSpaceShowFabOffset)
        {
            hideFab();
        }
        else{
            showFab();
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mPlayButton).cancel();
            ViewPropertyAnimator.animate(mPlayButton).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mPlayButton).cancel();
            ViewPropertyAnimator.animate(mPlayButton).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }
}
