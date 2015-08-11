package com.bettadapur.ruseandroid.eventing;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bettadapur.ruseandroid.model.Album;

/**
 * Created by Alex on 8/9/2015.
 */
public class OpenAlbumRequest
{
    private Album album;
    private MaterialDialog dialog;

    public OpenAlbumRequest(Album album)
    {
        this(album, null);
    }

    public OpenAlbumRequest(Album album, MaterialDialog dialog)
    {
        this.album = album;
        this.dialog = dialog;
    }

    public Album getAlbum() {
        return album;
    }

    public MaterialDialog getDialog() {
        return dialog;
    }
}
