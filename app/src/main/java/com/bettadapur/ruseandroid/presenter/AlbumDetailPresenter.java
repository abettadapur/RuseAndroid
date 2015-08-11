package com.bettadapur.ruseandroid.presenter;

import com.bettadapur.ruseandroid.net.RuseService;
import com.bettadapur.ruseandroid.ui.views.AlbumDetailView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import javax.inject.Inject;

/**
 * Created by Alex on 8/9/2015.
 */
public class AlbumDetailPresenter extends MvpBasePresenter<AlbumDetailView>
{
    RuseService ruseService;

    @Inject
    public AlbumDetailPresenter(RuseService ruseService)
    {
        this.ruseService = ruseService;
    }
}
