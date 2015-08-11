package com.bettadapur.ruseandroid.eventing;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bettadapur.ruseandroid.model.Album;
import com.bettadapur.ruseandroid.model.Artist;

/**
 * Created by Alex on 8/11/2015.
 */
public class OpenArtistRequest
{
    private Artist artist;
    private MaterialDialog dialog;

    public OpenArtistRequest(Artist artist)
    {
        this(artist, null);
    }

    public OpenArtistRequest(Artist artist, MaterialDialog dialog)
    {
        this.artist = artist;
        this.dialog = dialog;
    }

    public Artist getArtist() {
        return artist;
    }

    public MaterialDialog getDialog() {
        return dialog;
    }
}
