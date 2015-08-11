package com.bettadapur.ruseandroid.dagger;

import com.bettadapur.ruseandroid.presenter.AlbumDetailPresenter;
import com.bettadapur.ruseandroid.presenter.NowPlayingPresenter;
import com.bettadapur.ruseandroid.ui.fragments.AlbumDetailFragment;
import com.bettadapur.ruseandroid.ui.fragments.NowPlayingFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alex on 8/7/2015.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class)
public interface AlbumDetailComponent
{
    public void inject(AlbumDetailFragment fragment);

    public AlbumDetailPresenter presenter();
}
