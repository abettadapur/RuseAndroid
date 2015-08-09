package com.bettadapur.ruseandroid.net.retrofit;

import com.bettadapur.ruseandroid.model.SearchResult;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Alex on 8/8/2015.
 */
public interface RuseSearch
{
    @GET("/search/{query}")
    Observable<SearchResult> search(@Path("query") String query);
}
