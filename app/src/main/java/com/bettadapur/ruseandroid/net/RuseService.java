package com.bettadapur.ruseandroid.net;

import android.util.Log;

import com.bettadapur.ruseandroid.model.Album;
import com.bettadapur.ruseandroid.model.Artist;
import com.bettadapur.ruseandroid.model.SearchResult;
import com.bettadapur.ruseandroid.model.Song;
import com.bettadapur.ruseandroid.model.Status;
import com.bettadapur.ruseandroid.net.retrofit.RuseSearch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;
import ws.wamp.jawampa.PubSubData;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampError;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

/**
 * Created by Alex on 4/23/2015.
 */
public class RuseService
{
    private WampClient client;
    private Observable<String> statusSubscription;
    private Observable<String> queueSubscription;

    private Gson gson;
    private RuseSearch searchService;

    private static RuseService ruseService;


    public static RuseService GetInstance()
    {
        if(ruseService == null)
        {
            ruseService = new RuseService("ws://abettadapurlin2.cloudapp.net:5000/ws", new GsonBuilder().create());
        }
        return ruseService;
    }
    public RuseService(String endpoint, Gson gson)
    {
        this.gson = gson;
        try {

            WampClientBuilder builder = new WampClientBuilder();

            builder.withConnectorProvider(new NettyWampClientConnectorProvider())
                    .withUri(endpoint)
                    .withRealm("realm1")
                    .withInfiniteReconnects()
                    .withReconnectInterval(5, TimeUnit.SECONDS)
                    .withCloseOnErrors(false);

            client = builder.build();

        }
        catch(WampError e)
        {
            Log.e("RuseService", e.getMessage());
            return;
        }
        catch(Exception ex)
        {
            Log.e("RuseService", ex.getMessage());
            return;
        }

        if(client!=null) {
            client.statusChanged()
                    .subscribe((WampClient.State newState) -> {
                        if (newState instanceof WampClient.ConnectedState) {
                            Log.i("RuseService: "+this.toString(), "Connected to endpoint: "+endpoint);
                            statusSubscription = client.makeSubscription("com.ruse.now_playing", String.class);
                            queueSubscription = client.makeSubscription("com.ruse.queue", String.class);

                        } else if (newState instanceof WampClient.DisconnectedState)
                        {
                            //TODO: We should inform the fragments that we have lost connection
                        } else if (newState instanceof WampClient.ConnectingState) {
                            //TODO: We should inform the fragments that we are reconnecting
                        }
                    });
            client.open();
        }

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://abettadapurlin2.cloudapp.net:5001").build();
        searchService = restAdapter.create(RuseSearch.class);
    }

    public Observable<WampClient.State> clientStatus()
    {
        return client.statusChanged();
    }
    public Observable<Status> subscribeStatus()
    {
        return statusSubscription.map((s) -> gson.fromJson(s, Status.class));
    }
    public Observable<Song[]> subscribeQueue()
    {
        return queueSubscription.map((s)->gson.fromJson(s, Song[].class));
    }

    public Observable<SearchResult> search(String query)
    {
        return searchService.search(query);
    }
    public Observable<Album> getAlbum(String id)
    {
        Log.i("RuseService", "Loaded album: "+id);
        return client.call("com.ruse.get_album", String.class, id).map((s) -> gson.fromJson(s, Album.class));
    }
    public Observable<Artist> getArtist(String id)
    {
        Log.i("RuseService", "Loaded artist: "+id);
        return client.call("com.ruse.get_artist", String.class, id).map((s)->gson.fromJson(s, Artist.class));
    }

    public void requestQueue(){client.publish("com.ruse.queue_request", "");}

    public void playSong(String id)
    {
        Log.i("RuseService", "Playing song with id: "+id);
        client.call("com.ruse.play_song", id);
    }
    public void queueSong(String id)
    {
        Log.i("RuseService", "Queueing song with id: "+id);
        client.call("com.ruse.queue_song", id);
    }
    public void playAlbum(String id)
    {
        Log.i("RuseService", "Playing album with id: "+id);
        client.call("com.ruse.play_album",  id);
    }
    public void queueAlbum(String id)
    {
        Log.i("RuseService", "Queueing album with id: "+id);
        client.call("com.ruse.queue_album",  id);
    }
    public void setVolume(int value)
    {
        Log.i("RuseService", "Setting volume to: "+value);
        client.call("com.ruse.set_volume",  value);
    }
    public void pause()
    {
        Log.i("RuseService", "Sending pause command");
        client.call("com.ruse.pause");
    }
    public void resume()
    {
        Log.i("RuseService", "Sending resume command");
        client.call("com.ruse.resume");
    }
    public void next()
    {
        Log.i("RuseService", "Sending skip command");
        client.call("com.ruse.next");
    }
    public void prev()
    {
        Log.i("RuseService", "Sending prev command");
        client.call("com.ruse.prev");
    }
    public void delete(int id)
    {
        Log.i("RuseService", "Deleting song in queue position: "+id);
        client.call("com.ruse.delete", id);
    }
    public void go_to(int id)
    {
        Log.i("RuseService", "Skipping to queue position: "+id);
        client.call("com.ruse.go_to", id);
    }
    public void flush()
    {
        Log.i("RuseService", "Sending flush command");
        client.call("com.ruse.flush");
    }


}
