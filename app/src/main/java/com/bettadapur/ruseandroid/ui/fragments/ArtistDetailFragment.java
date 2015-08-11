package com.bettadapur.ruseandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.test.ApplicationTestCase;
import android.view.View;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.net.RuseService;
import com.hannesdorfmann.mosby.MosbyFragment;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by Alex on 8/11/2015.
 */
public class ArtistDetailFragment extends MosbyFragment
{

    @Inject
    RuseService ruseService;

    @Inject
    Bus bus;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_artist_view;
    }

    @Override
    protected void injectDependencies() {
        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(getActivity())).build();
        component.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
