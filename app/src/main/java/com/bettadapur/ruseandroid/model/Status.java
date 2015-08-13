package com.bettadapur.ruseandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alex on 5/3/2015.
 */
public class Status implements Parcelable
{
    private int volume;
    private int time;
    private int length;
    private boolean playing;
    private Song current;

    public Status()
    {

    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Song getCurrent() {
        return current;
    }

    public void setCurrent(Song current) {
        this.current = current;
    }

    public Status(Parcel in)
    {
        volume = in.readInt();
        time = in.readInt();
        length = in.readInt();
        playing = in.readByte()!=0x00;
        current = in.readParcelable(Song.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(volume);
        parcel.writeInt(time);
        parcel.writeInt(length);
        parcel.writeByte((byte)(playing?0x01:0x00));
        parcel.writeParcelable(current, 0);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Status> CREATOR = new Parcelable.Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
}
