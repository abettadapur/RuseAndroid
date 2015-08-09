package com.bettadapur.ruseandroid.dagger;

import com.bettadapur.ruseandroid.presenter.SearchPresenter;
import com.bettadapur.ruseandroid.ui.fragments.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alex on 8/7/2015.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class)
public interface SearchComponent
{
    public void inject(SearchFragment fragment);

    public SearchPresenter presenter();
}
