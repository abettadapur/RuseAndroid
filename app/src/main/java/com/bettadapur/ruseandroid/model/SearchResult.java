package com.bettadapur.ruseandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 5/3/2015.
 */
public class SearchResult
{
    @SerializedName("song_hits")
    private Song[] songs;
    @SerializedName("album_hits")
    private Album[] albums;
    @SerializedName("artist_hits")
    private Artist[] artists;

    public SearchResult()
    {}

    public Song[] getSongs() {
        return songs;
    }

    public void setSongs(Song[] songs) {
        this.songs = songs;
    }

    public Album[] getAlbums() {
        return albums;
    }

    public void setAlbums(Album[] albums) {
        this.albums = albums;
    }

    public Artist[] getArtists() {
        return artists;
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {

        //TODO: Investigate this warning
        StringBuilder sb = new StringBuilder();
        sb.append("Results: \n");
        sb.append("Songs: ");
        sb.append(songs.length);
        sb.append("results");
        sb.append("Albums: ");
        sb.append(albums.length);
        sb.append("results");
        sb.append("Artists: ");
        sb.append(artists.length);
        sb.append("results");

        return sb.toString();

    }
}
