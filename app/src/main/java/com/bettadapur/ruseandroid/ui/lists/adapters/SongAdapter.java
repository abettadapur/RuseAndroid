package com.bettadapur.ruseandroid.ui.lists.adapters;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.net.RuseService;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alex on 8/7/2015.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>
{

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.titleView)
        public TextView titleView;
        @Bind(R.id.artistView)
        public TextView artistView;
        @Bind(R.id.artView)
        public ImageView artView;
        @Bind(R.id.overflowButton)
        public ImageButton overflowButton;
        @Bind(R.id.songContainer)
        public LinearLayout container;


        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    protected List<Song> mSongList;
    protected Context mContext;

    @Inject
    protected RuseService mRuseService;

    public SongAdapter(List<Song> items, Context context)
    {
        mSongList = items;
        mContext = context;

        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(context)).build();
        component.inject(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_song, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Song song = mSongList.get(position);
        holder.titleView.setText(song.getTitle());
        holder.artistView.setText(song.getArtist());

        Picasso.with(mContext).load(song.getArtSrc()).into(holder.artView);
        setUpListeners(holder, song);

    }

    protected void setUpListeners(ViewHolder holder, Song song) {
        holder.overflowButton.setOnClickListener((v)->
        {
            PopupMenu popupMenu = new PopupMenu(mContext, holder.overflowButton);
            popupMenu.getMenuInflater().inflate(R.menu.search_song_overflow_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener((item)->
            {
                switch(item.getItemId())
                {
                    case R.id.now:
                        mRuseService.playSong(song.getId());
                        break;
                    case R.id.last:
                        mRuseService.queueSong(song.getId());
                        break;
                    default:
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
        holder.container.setOnClickListener((v)->
                mRuseService.playSong(song.getId()));
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }


}
