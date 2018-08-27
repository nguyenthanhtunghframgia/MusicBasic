package com.example.nguyenthanhtungh.musicapplication.data.model;

import java.io.Serializable;

public class Song implements Serializable {
    private String mName;
    private String mPath;
    private String mAlbum;
    private String mArtist;
    private int mDuration;

    public Song(String name, String path, String album, String artist, int duration) {
        mName = name;
        mPath = path;
        mAlbum = album;
        mArtist = artist;
        mDuration = duration;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
