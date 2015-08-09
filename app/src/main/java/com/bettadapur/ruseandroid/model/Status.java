package com.bettadapur.ruseandroid.model;

/**
 * Created by Alex on 5/3/2015.
 */
public class Status
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
}
