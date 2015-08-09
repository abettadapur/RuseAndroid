package com.bettadapur.ruseandroid.ui.lists.adapters;

import android.content.Context;
import android.widget.PopupMenu;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.model.Song;

import java.util.List;


/**
 * Created by Alex on 8/9/2015.
 */
public class NowPlayingAdapter extends SongAdapter {

    public NowPlayingAdapter(List<Song> items, Context context) {
        super(items, context);
    }

    @Override
    protected void setUpListeners(ViewHolder holder, Song song)
    {
        holder.overflowButton.setOnClickListener((v)->
        {
            PopupMenu popupMenu = new PopupMenu(mContext, holder.overflowButton);
            popupMenu.getMenuInflater().inflate(R.menu.now_playing_overflow_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener((item)->
            {
                switch(item.getItemId())
                {
                    case R.id.now:
                        mRuseService.go_to(song.getVlcId());
                        break;
                    case R.id.last:
                        mRuseService.delete(song.getVlcId());
                        break;
                    default:
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
        holder.container.setOnClickListener((v)->
                mRuseService.go_to(song.getVlcId()));
    }


}
