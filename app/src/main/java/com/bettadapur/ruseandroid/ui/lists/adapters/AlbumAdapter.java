package com.bettadapur.ruseandroid.ui.lists.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.eventing.OpenAlbumRequest;
import com.bettadapur.ruseandroid.model.Album;
import com.bettadapur.ruseandroid.net.RuseService;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alex on 8/9/2015.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>
{
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.titleView)
        public TextView titleView;
        @Bind(R.id.artistView)
        public TextView artistView;
        @Bind(R.id.artView)
        public ImageView artView;
        @Bind(R.id.albumContainer)
        public CardView container;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Album> mAlbumList;
    private Context mContext;

    @Inject
    RuseService ruseService;

    @Inject
    Bus bus;

    public AlbumAdapter(List<Album> items, Context context)
    {
        mContext = context;
        mAlbumList = items;
        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(mContext)).build();
        component.inject(this);
        bus.register(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_album, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Album album = mAlbumList.get(position);

        holder.titleView.setText(album.getName());
        holder.artistView.setText(album.getArtistName());
        Picasso.with(mContext).load(album.getArtSrc()).into(holder.artView);

        holder.container.setOnClickListener((v)->
        {

            MaterialDialog dialog = new MaterialDialog.Builder(mContext).title("Loading album...").content("Loading...").progress(true, 0).show();
            ruseService.getAlbum(album.getId()).subscribe((a)->
            {
                bus.post(new OpenAlbumRequest(a, dialog));
            });

        });
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }
}
