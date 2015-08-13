package com.bettadapur.ruseandroid.ui.lists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.Album;
import com.bettadapur.ruseandroid.model.Artist;
import com.bettadapur.ruseandroid.model.SearchResult;
import com.bettadapur.ruseandroid.ui.lists.adapters.AlbumAdapter;
import com.bettadapur.ruseandroid.ui.lists.adapters.ArtistAdapter;
import com.bettadapur.ruseandroid.ui.views.SearchChild;
import com.hannesdorfmann.mosby.MosbyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import icepick.Icepick;
import icepick.State;

/**
 * Created by Alex on 8/7/2015.
 */
public class ArtistListFragment extends MosbyFragment implements SearchChild
{

    @Bind(R.id.genericListView)
    RecyclerView mRecyclerView;

    @Bind(R.id.loadingCircle)
    ProgressBar mLoadingCircle;

    private ArtistAdapter mArtistAdapter;

    protected List<Artist> mArtistList;

    public ArtistListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null)
            mArtistList = savedInstanceState.getParcelableArrayList("artists");
        else
            mArtistList = new ArrayList<>();

        mArtistAdapter = new ArtistAdapter(mArtistList, getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("artists", (ArrayList<Artist>)mArtistList);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_generic_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mArtistAdapter);
    }

    @Override
    public void setLoading(boolean loading)
    {
        if(mRecyclerView != null && mLoadingCircle != null) {
            mRecyclerView.setVisibility(loading ? View.GONE : View.VISIBLE);
            mLoadingCircle.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setResults(SearchResult result)
    {
        if(mArtistAdapter!=null && mArtistList != null)
        {
            mArtistList.clear();
            for(Artist a : result.getArtists())
            {
                mArtistList.add(a);
            }
            mArtistAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void clearResults()
    {
        mArtistList.clear();

        if(mArtistAdapter!=null)
            mArtistAdapter.notifyDataSetChanged();
    }
}
