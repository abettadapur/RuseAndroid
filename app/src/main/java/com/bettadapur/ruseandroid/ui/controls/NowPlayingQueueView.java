package com.bettadapur.ruseandroid.ui.controls;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.ui.lists.adapters.NowPlayingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alex on 8/7/2015.
 */
public class NowPlayingQueueView extends FrameLayout
{
    private List<Song> mQueue;
    private NowPlayingAdapter mAdapter;
    private Context mContext;

    @Bind(R.id.genericListView)
    RecyclerView mRecyclerView;

    public NowPlayingQueueView(Context context)
    {
        super(context);
        mQueue = new ArrayList<>();
        mContext = context;
        mAdapter = new NowPlayingAdapter(mQueue, mContext);
        initializeViews();
    }

    private void initializeViews()
    {
        inflate(mContext, R.layout.fragment_generic_list, this);
        ButterKnife.bind(this);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }
    public void updateQueue(Song[] songs)
    {
        mQueue.clear();
        for(Song s : songs)
        {
            mQueue.add(s);
        }
        mAdapter.notifyDataSetChanged();
    }
}
