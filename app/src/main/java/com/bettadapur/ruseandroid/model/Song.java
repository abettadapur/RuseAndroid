package com.bettadapur.ruseandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 5/3/2015.
 */
public class Song implements Parcelable
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
    private int durationMillis;

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

    public String getDuration()
    {
        long second = (durationMillis / 1000) % 60;
        long minute = (durationMillis / (1000 * 60));
        String time = String.format("%d:%02d", minute, second);
        return time;
    }

    public Song(Parcel in)
    {
        album = in.readString();
        albumId = in.readString();
        artist = in.readString();
        artistId = in.readString();
        id = in.readString();
        artSrc = in.readString();
        title = in.readString();
        vlcId = in.readInt();
        current = in.readByte() != 0x00;
        durationMillis = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(album);
        parcel.writeString(albumId);
        parcel.writeString(artist);
        parcel.writeString(artistId);
        parcel.writeString(id);
        parcel.writeString(artSrc);
        parcel.writeString(title);
        parcel.writeInt(vlcId);
        parcel.writeByte((byte)(current ? 0x01: 0x00));
        parcel.writeInt(durationMillis);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
