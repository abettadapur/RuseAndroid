package com.bettadapur.ruseandroid.presenter;

import android.util.Log;

import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.model.Status;
import com.bettadapur.ruseandroid.net.RuseService;
import com.bettadapur.ruseandroid.ui.views.NowPlayingView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import javax.inject.Inject;

import rx.Subscription;
import ws.wamp.jawampa.WampClient;

/**
 * Created by Alex on 8/7/2015.
 */
public class NowPlayingPresenter extends MvpBasePresenter<NowPlayingView>
{
    private RuseService ruseService;
    private Subscription statusSubscription, queueSubsciption;

    @Inject
    public NowPlayingPresenter(RuseService service)
    {
        this.ruseService = service;
    }

    public void onResume()
    {
        this.ruseService.clientStatus().subscribe((state)->
        {
            if (state instanceof WampClient.ConnectedState)
            {
                statusSubscription = ruseService.subscribeStatus().subscribe(
                        (status)->
                        {
                            updateStatus(status);
                        },
                        (error)->
                        {
                            Log.e("RuseService", "Error in status subscription: "+error.getMessage());
                        },
                        //onCompleted
                        ()->
                        {
                            Log.e("RuseService", "Status subscription onComplete()");
                        }
                );
                queueSubsciption = ruseService.subscribeQueue().subscribe(this::updateQueue);
                this.ruseService.requestQueue();
            }
        });
    }

    private void updateQueue(Song[] queue)
    {
        if(isViewAttached())
            getView().updateQueue(queue);
    }

    private void updateStatus(Status status)
    {
        if(isViewAttached())
            getView().updateStatus(status);
    }

    public void onPause()
    {
        if(statusSubscription!=null)
            statusSubscription.unsubscribe();
        if(queueSubsciption!=null)
            queueSubsciption.unsubscribe();
    }
}
