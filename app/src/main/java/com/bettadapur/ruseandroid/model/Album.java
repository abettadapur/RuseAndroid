package com.bettadapur.ruseandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 5/3/2015.
 */
public class Album
{
    private String name;

    @SerializedName("albumId")
    private String id;

    @SerializedName("artist")
    private String artistName;

    private String artistId;

    private int year;

    @SerializedName("albumArtRef")
    private String artSrc;

    @SerializedName("tracks")
    private Song[] songs;

    public Album()
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getArtSrc() {
        return artSrc;
    }

    public void setArtSrc(String artSrc) {
        this.artSrc = artSrc;
    }

    public Song[] getSongs() {
        return songs;
    }

    public void setSongs(Song[] songs) {
        this.songs = songs;
    }
}
