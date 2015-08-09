package com.bettadapur.ruseandroid.ui.views;

import com.bettadapur.ruseandroid.model.SearchResult;

/**
 * Created by Alex on 8/7/2015.
 */
public interface SearchChild
{
    public void setLoading(boolean loading);
    public void setResults(SearchResult result);
    public void clearResults();
}
