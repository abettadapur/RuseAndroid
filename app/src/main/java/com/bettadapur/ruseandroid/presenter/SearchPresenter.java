package com.bettadapur.ruseandroid.presenter;

import android.util.Log;

import com.bettadapur.ruseandroid.net.RuseService;
import com.bettadapur.ruseandroid.ui.views.SearchView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by Alex on 8/7/2015.
 */
public class SearchPresenter extends MvpBasePresenter<SearchView>
{
    private RuseService ruseService;

    @Inject
    public SearchPresenter(RuseService service)
    {
        this.ruseService = service;
    }

    public void performSearch(String query)
    {
        if(isViewAttached())
        {
            getView().clearResults();
            getView().setLoading(true);
            ruseService.search(query).subscribe((result)->
            {
                getView().setResults(result);
                getView().setLoading(false);
            },
            (error) ->
            {
                Log.e("SearchPresenter", "An error occured while searching: "+error.getMessage());
                getView().setLoading(false);
            });
        }
    }
}
