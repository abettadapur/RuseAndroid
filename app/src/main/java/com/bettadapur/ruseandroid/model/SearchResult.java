package com.bettadapur.ruseandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 5/3/2015.
 */
public class SearchResult implements Parcelable
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

    public SearchResult(Parcel in)
    {
        songs = in.createTypedArray(Song.CREATOR);
        albums = in.createTypedArray(Album.CREATOR);
        artists = in.createTypedArray(Artist.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeTypedArray(songs, 0);
        parcel.writeTypedArray(albums, 0);
        parcel.writeTypedArray(artists, 0);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SearchResult> CREATOR = new Parcelable.Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };
}
