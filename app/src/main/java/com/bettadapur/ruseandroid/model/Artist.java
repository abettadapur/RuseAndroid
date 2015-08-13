package com.bettadapur.ruseandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 5/3/2015.
 */
public class Artist implements Parcelable
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

    public Artist(Parcel in)
    {
        name = in.readString();
        id = in.readString();
        artSrc = in.readString();
        topSongs = in.createTypedArray(Song.CREATOR);
        albums = in.createTypedArray(Album.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(artSrc);
        parcel.writeTypedArray(topSongs, 0);
        parcel.writeTypedArray(albums, 0);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
