package com.bettadapur.ruseandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 5/3/2015.
 */
public class Album implements Parcelable
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


    public Album(Parcel in)
    {
        name = in.readString();
        id = in.readString();
        artistName = in.readString();
        artistId = in.readString();
        year = in.readInt();
        artSrc = in.readString();
        songs = in.createTypedArray(Song.CREATOR);
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
        parcel.writeString(artistName);
        parcel.writeString(artistId);
        parcel.writeInt(year);
        parcel.writeString(artSrc);
        parcel.writeTypedArray(songs, 0);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
