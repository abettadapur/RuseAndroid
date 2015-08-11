package com.bettadapur.ruseandroid.dagger;

import com.bettadapur.ruseandroid.net.RuseService;
import com.bettadapur.ruseandroid.presenter.NowPlayingPresenter;
import com.bettadapur.ruseandroid.ui.activities.MainActivity;
import com.bettadapur.ruseandroid.ui.controls.NowPlayingControlsView;
import com.bettadapur.ruseandroid.ui.controls.NowPlayingControlsView$$ViewBinder;
import com.bettadapur.ruseandroid.ui.controls.NowPlayingQueueView;
import com.bettadapur.ruseandroid.ui.controls.NowPlayingSummaryView;
import com.bettadapur.ruseandroid.ui.fragments.NowPlayingFragment;
import com.bettadapur.ruseandroid.ui.fragments.SearchFragment;
import com.bettadapur.ruseandroid.ui.lists.adapters.AlbumAdapter;
import com.bettadapur.ruseandroid.ui.lists.adapters.AlbumSongAdapter;
import com.bettadapur.ruseandroid.ui.lists.adapters.SongAdapter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alex on 8/7/2015.
 */

@Singleton
@Component(modules = {RuseModule.class})
public interface ApplicationComponent
{

    public void inject(MainActivity activity);
    public void inject(NowPlayingQueueView view);
    public void inject(NowPlayingSummaryView view);
    public void inject(NowPlayingControlsView view);
    public void inject(SongAdapter adapter);
    public void inject(AlbumAdapter albumAdapter);


    public RuseService ruseService();
    public Bus bus();


    void inject(AlbumSongAdapter albumSongAdapter);
}
