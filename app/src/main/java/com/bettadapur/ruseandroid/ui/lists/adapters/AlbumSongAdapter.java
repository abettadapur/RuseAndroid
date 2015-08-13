package com.bettadapur.ruseandroid.ui.lists.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.net.RuseService;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Alex on 8/10/2015.
 */
public class AlbumSongAdapter extends ArrayAdapter<Song>
{
    private Context mContext;
    private List<Song> mSongList;

    @Inject
    RuseService ruseService;

    public AlbumSongAdapter(Context context, int resource, List<Song> songs)
    {
        super(context, resource, songs);
        mContext = context;
        mSongList = songs;

        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(context)).build();
        component.inject(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = mSongList.get(position);
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.listitem_albumsong, parent, false);

        CardView container = (CardView)v.findViewById(R.id.card_view);
        TextView numberView = (TextView)v.findViewById(R.id.numberView);
        TextView durationView = (TextView)v.findViewById(R.id.durationView);
        TextView titleView = (TextView)v.findViewById(R.id.titleView);
        ImageButton overflowButton = (ImageButton)v.findViewById(R.id.overflowButton);

        numberView.setText(position+1+"");
        durationView.setText(song.getDuration());
        titleView.setText(song.getTitle());

        //container.setOnClickListener((view)-> {
        // ruseService.playSong(song.getId());
        //SnackbarManager.show(Snackbar.with(mContext).text("Now playing "+song.getTitle()));
        // }
        // );

        overflowButton.setOnClickListener((button)->
        {
            PopupMenu popupMenu = new PopupMenu(mContext, button);
            popupMenu.getMenuInflater().inflate(R.menu.album_song_overflow_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener((item)->
            {
                switch(item.getItemId())
                {
                    case R.id.now:
                        ruseService.playSong(song.getId());
                        SnackbarManager.show(Snackbar.with(mContext).text("Now playing " + song.getTitle()));
                        break;
                    case R.id.last:
                        ruseService.queueSong(song.getId());
                        SnackbarManager.show(Snackbar.with(mContext).text("Added "+song.getTitle()+" to queue"));
                        break;
                    default:
                        break;
                }
                return true;
            });
            popupMenu.show();
        });

        return v;
    }

    @Override
    public int getCount() {
        return mSongList.size();
    }
}
