package com.bettadapur.ruseandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 5/3/2015.
 */
public class Song
{
    private String album;
    private String albumId;
    private String artist;
    private String artistId;
    @SerializedName("nid")
    private String id;
    @SerializedName("albumArtRef")
    private String artSrc;
    public String title;
    @SerializedName("vlcid")
    private int vlcId;
    private boolean current;

    public Song()
    {}

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVlcId() {
        return vlcId;
    }

    public void setVlcId(int vlcId) {
        this.vlcId = vlcId;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
