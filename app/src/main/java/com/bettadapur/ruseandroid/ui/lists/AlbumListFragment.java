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
import com.bettadapur.ruseandroid.model.SearchResult;
import com.bettadapur.ruseandroid.ui.lists.adapters.AlbumAdapter;
import com.bettadapur.ruseandroid.ui.views.SearchChild;
import com.hannesdorfmann.mosby.MosbyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Alex on 8/7/2015.
 */
public class AlbumListFragment extends MosbyFragment implements SearchChild
{

    @Bind(R.id.genericListView)
    RecyclerView mRecyclerView;

    @Bind(R.id.loadingCircle)
    ProgressBar mLoadingCircle;

    private AlbumAdapter mAlbumAdapter;
    private List<Album> mAlbumList;

    public AlbumListFragment()
    {
        mAlbumList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumAdapter = new AlbumAdapter(mAlbumList, getActivity());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_generic_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAlbumAdapter);
    }

    @Override
    public void setLoading(boolean loading)
    {
        mRecyclerView.setVisibility(loading ? View.GONE : View.VISIBLE);
        mLoadingCircle.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setResults(SearchResult result)
    {
        mAlbumList.clear();
        for(Album a : result.getAlbums())
        {
            mAlbumList.add(a);
        }
        mAlbumAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearResults()
    {
        mAlbumList.clear();
        mAlbumAdapter.notifyDataSetChanged();
    }
}
