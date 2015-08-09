package com.bettadapur.ruseandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 5/3/2015.
 */
public class Artist
{
    private String name;

    @SerializedName("artistId")
    private String id;

    @SerializedName("artistArtRef")
    private String artSrc;

    @SerializedName("topTracks")
    private Song[] topSongs;

    private Album[] albums;

    public Artist()
    {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtSrc() {
        return artSrc;
    }

    public void setArtSrc(String artSrc) {
        this.artSrc = artSrc;
    }

    public Song[] getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(Song[] topSongs) {
        this.topSongs = topSongs;
    }

    public Album[] getAlbums() {
        return albums;
    }

    public void setAlbums(Album[] albums) {
        this.albums = albums;
    }
}
