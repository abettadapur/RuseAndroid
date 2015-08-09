package com.bettadapur.ruseandroid.dagger;

import android.content.Context;

import com.bettadapur.ruseandroid.net.RuseService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alex on 8/6/2015.
 */
@Module
public class RuseModule {

    private Context context;
    public RuseModule(Context context)
    {
        this.context = context;
    }

    @Provides public Context provideContext() {return context;}

    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public RuseService provideRuseService() {
        return RuseService.GetInstance();
    }

    @Provides
    @Singleton
    Picasso providePicasso()
    {
        return Picasso.with(context);
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return BusWrapper.GetInstance();
    }
}
