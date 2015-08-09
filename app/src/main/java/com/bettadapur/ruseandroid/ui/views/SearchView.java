package com.bettadapur.ruseandroid.ui.views;

import com.bettadapur.ruseandroid.model.SearchResult;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Alex on 8/7/2015.
 */
public interface SearchView extends MvpView
{
    public void setLoading(boolean loading);
    public void setResults(SearchResult result);
    public void clearResults();
}
