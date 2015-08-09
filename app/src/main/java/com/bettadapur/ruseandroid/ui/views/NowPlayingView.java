package com.bettadapur.ruseandroid.ui.views;

import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.model.Status;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Alex on 8/7/2015.
 */
public interface NowPlayingView extends MvpView
{
    public void updateStatus(Status status);
    public void updateQueue(Song[] songs);
    public void setExpanded(boolean b);
    public void openQueue();
    public void closeQueue();
}
