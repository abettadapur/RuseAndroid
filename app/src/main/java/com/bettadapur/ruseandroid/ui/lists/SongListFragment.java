package com.bettadapur.ruseandroid.ui.lists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.SearchResult;
import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.net.RuseService;
import com.bettadapur.ruseandroid.ui.lists.adapters.SongAdapter;
import com.bettadapur.ruseandroid.ui.views.SearchChild;
import com.hannesdorfmann.mosby.MosbyActivity;
import com.hannesdorfmann.mosby.MosbyFragment;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Alex on 8/7/2015.
 */
public class SongListFragment extends MosbyFragment implements SearchChild
{
    @Bind(R.id.genericListView)
    RecyclerView mRecyclerView;

    @Bind(R.id.loadingCircle)
    ProgressBar mLoadingCircle;

    private SongAdapter mSongAdapter;
    private List<Song> mSongList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSongList = new ArrayList<>();
        mSongAdapter = new SongAdapter(mSongList, getActivity());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_generic_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setAdapter(mSongAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void setLoading(boolean loading)
    {
        mRecyclerView.setVisibility(loading?View.GONE:View.VISIBLE);
        mLoadingCircle.setVisibility(loading?View.VISIBLE:View.GONE);
    }

    @Override
    public void setResults(SearchResult result)
    {
        mSongList.clear();
        for(Song s: result.getSongs())
        {
            mSongList.add(s);
        }
        mSongAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearResults()
    {
        mSongList.clear();
        mSongAdapter.notifyDataSetChanged();
    }
}
