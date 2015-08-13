package com.bettadapur.ruseandroid.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.astuetz.PagerSlidingTabStrip;
import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerSearchComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.dagger.SearchComponent;
import com.bettadapur.ruseandroid.model.SearchResult;
import com.bettadapur.ruseandroid.presenter.SearchPresenter;
import com.bettadapur.ruseandroid.ui.lists.AlbumListFragment;
import com.bettadapur.ruseandroid.ui.lists.ArtistListFragment;
import com.bettadapur.ruseandroid.ui.lists.SongListFragment;
import com.bettadapur.ruseandroid.ui.views.SearchChild;
import com.bettadapur.ruseandroid.ui.views.SearchView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.squareup.otto.Bus;


import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Alex on 8/7/2015.
 */
public class SearchFragment extends MvpFragment<SearchView, SearchPresenter> implements SearchView, android.support.v7.widget.SearchView.OnQueryTextListener
{
    @Bind(R.id.tabs)
    PagerSlidingTabStrip mTabStrip;

    @Bind(R.id.pager)
    ViewPager mViewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    Bus bus;

    private SearchPresenter mPresenter;
    private SearchComponent mSearchComponent;
    private SearchPagerAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPagerAdapter = new SearchPagerAdapter(getChildFragmentManager());
        //setHasOptionsMenu(true);
    }

    @Override
    protected void injectDependencies()
    {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().ruseModule(new RuseModule(getActivity())).build();
        mSearchComponent = DaggerSearchComponent.builder().applicationComponent(applicationComponent).build();
        mSearchComponent.inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mPagerAdapter);
        mTabStrip.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

        toolbar.setTitle("RuseAndroid");
        toolbar.inflateMenu(R.menu.menu_main);
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.search).setIcon(
                new IconDrawable(getActivity(), Iconify.IconValue.fa_search)
                        .color(0xFFFFFF)
                        .actionBarSize());

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView)menu.findItem(R.id.search).getActionView();

        if(searchView!=null)
        {
            searchView.setOnQueryTextListener(this);
        }
    }

    @Override
    public SearchPresenter createPresenter() {
        mPresenter = mSearchComponent.presenter();
        return mPresenter;
    }

    public void performSearch(String query)
    {
        presenter.performSearch(query);
    }
    @Override
    public void setResults(SearchResult result) {
        getActivity().runOnUiThread(() -> mPagerAdapter.setResults(result));
    }

    @Override
    public void clearResults() {
        getActivity().runOnUiThread(mPagerAdapter::clearResults);
    }

    @Override
    public void setLoading(boolean loading)
    {
        getActivity().runOnUiThread(()-> {
            if (loading)
                ((InputMethodManager)(getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromInputMethod(getView().getWindowToken(), 0);
            mPagerAdapter.setLoading(loading);
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        InputMethodManager inputManager =
                (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getActivity().getCurrentFocus().getWindowToken(),0);
        performSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private class SearchPagerAdapter extends FragmentPagerAdapter
    {
        private final String[] TITLES = {"Songs", "Albums", "Artists"};
        private SearchChild[] fragments;

        private SongListFragment songFragment;
        private AlbumListFragment albumFragment;
        private ArtistListFragment artistFragment;

        public SearchPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new SearchChild[3];
            songFragment = new SongListFragment();
            albumFragment = new AlbumListFragment();
            artistFragment = new ArtistListFragment();

            fragments[0] = songFragment;
            fragments[1] = albumFragment;
            fragments[2] = artistFragment;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int i) {
            return (Fragment)fragments[i];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        public void setResults(SearchResult result)
        {
            for(SearchChild c : fragments)
            {
                c.setResults(result);
            }
        }

        public void clearResults()
        {
            for(SearchChild c : fragments)
            {
                c.clearResults();
            }
        }

        public void setLoading(boolean loading)
        {
            for(SearchChild c : fragments)
            {
                c.setLoading(loading);
            }
        }
    }
}
