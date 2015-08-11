package com.bettadapur.ruseandroid.ui.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.AlbumDetailComponent;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerAlbumDetailComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.Album;
import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.net.RuseService;
import com.bettadapur.ruseandroid.presenter.AlbumDetailPresenter;
import com.bettadapur.ruseandroid.ui.activities.MainActivity;
import com.bettadapur.ruseandroid.ui.lists.adapters.AlbumSongAdapter;
import com.bettadapur.ruseandroid.ui.views.AlbumDetailView;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.hannesdorfmann.mosby.MosbyActivity;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.melnykov.fab.FloatingActionButton;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Alex on 8/9/2015.
 */
public class AlbumDetailFragment extends MvpFragment<AlbumDetailView, AlbumDetailPresenter> implements AlbumDetailView, ObservableScrollViewCallbacks {

    @Bind(R.id.artView)
    ImageView mArtView;

    @Bind(R.id.songList)
    ObservableListView mSongList;

    @Bind(R.id.titleView)
    TextView mTitleView;

    @Bind(R.id.playButton)
    FloatingActionButton mPlayButton;

    @Bind(R.id.overlay)
    View mOverlay;

    @Bind(R.id.list_background)
    View mListBackground;

    @Inject
    RuseService ruseService;

    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;

    private String mCurrentId;
    private AlbumSongAdapter mAdapter;
    private AlbumDetailComponent mComponent;
    private AlbumDetailPresenter mPresenter;

    private Album mCurrentAlbum;
    private List<Song> mCurrentSongs;

    public AlbumDetailFragment()
    {

    }



    @Override
    public AlbumDetailPresenter createPresenter() {
        mPresenter = mComponent.presenter();
        return mPresenter;
    }



    @Override
    protected void injectDependencies()
    {
        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(getActivity())).build();
        mComponent = DaggerAlbumDetailComponent.builder().applicationComponent(component).build();
        mComponent.inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_album_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);

        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[] { android.R.attr.actionBarSize };
        int indexOfAttrActionSize = 0;
        TypedArray a = getActivity().obtainStyledAttributes(typedValue.data, textSizeAttr);
        mActionBarSize = a.getDimensionPixelSize(indexOfAttrActionSize, 0);
        a.recycle();

        mSongList.setScrollViewCallbacks(this);

        mSongList.setDivider(null);
        mSongList.setDividerHeight(0);

        View paddingView = new View(getActivity());
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, mFlexibleSpaceImageHeight);
        paddingView.setLayoutParams(lp);

        paddingView.setClickable(true);

        mSongList.addHeaderView(paddingView);

        mCurrentSongs = new ArrayList<>();
        mAdapter = new AlbumSongAdapter(getActivity(), R.layout.listitem_albumsong, mCurrentSongs);
        mSongList.setAdapter(mAdapter);

        mPlayButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_play).color(0xFFFFFF).actionBarSize());
        mPlayButton.setOnClickListener((v)->ruseService.playAlbum(mCurrentAlbum.getId()));
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);

        refreshViews();


    }

    @Override
    public void onResume() {
        super.onResume();
      //  ((MosbyActivity)getActivity()).getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(()->{onScrollChanged(1, true, false);/*showFab();*/},10);
        //handler.postDelayed(()->mSongList.smoothScrollBy(1, 1), 10);

    }

    @Override
    public void onPause() {
        super.onPause();
        //
        // ((MosbyActivity)getActivity()).getSupportActionBar().show();
    }

    public void updateAlbum(Album a)
    {
        mCurrentAlbum = a;
    }

    public void updateAlbumandRefresh(Album a)
    {
        updateAlbum(a);
        refreshViews();
    }

    public void refreshViews()
    {
        if(mTitleView!=null) {
            mTitleView.setText(mCurrentAlbum.getName());
            Picasso.with(getActivity()).load(mCurrentAlbum.getArtSrc()).into(mArtView);
            mCurrentSongs.clear();
            for (Song s : mCurrentAlbum.getSongs()) {
                mCurrentSongs.add(s);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initializeViews()
    {

    }

    @Override
    public void onScrollChanged(int y, boolean firstScroll, boolean dragging)
    {
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlay.getHeight();
        mOverlay.setTranslationY(ScrollUtils.getFloat(-y, minOverlayTransitionY, 0));
        mArtView.setTranslationY(ScrollUtils.getFloat(-y/2, minOverlayTransitionY,0));

        mListBackground.setTranslationY(Math.max(0, -y + mFlexibleSpaceImageHeight));

        mOverlay.setAlpha(ScrollUtils.getFloat((float)y/flexibleRange, 0, 1));

        float scale = 1+ScrollUtils.getFloat((flexibleRange-y)/flexibleRange, 0, 0.3f);
        setPivotXToTitle();
        mTitleView.setPivotY(0);
        mTitleView.setScaleX(scale);
        mTitleView.setScaleY(scale);

        int maxTitleTranslationY = (int)(mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - y;
        mTitleView.setTranslationY(titleTranslationY);

        int maxFABTranslationY = mFlexibleSpaceImageHeight - mPlayButton.getHeight()/2;
        float fabTranslationY = ScrollUtils.getFloat(-y + mFlexibleSpaceImageHeight -mPlayButton.getHeight()/2, mActionBarSize - mPlayButton.getHeight()/2, maxFABTranslationY);

        mPlayButton.setTranslationX(mOverlay.getWidth() - mFabMargin - mPlayButton.getWidth());
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

    private void setPivotXToTitle()
    {
        mTitleView.setPivotX(0);
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
