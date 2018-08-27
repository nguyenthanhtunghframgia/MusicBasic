package com.example.nguyenthanhtungh.musicapplication.data.repository;

import android.annotation.SuppressLint;

import com.example.nguyenthanhtungh.musicapplication.data.model.Song;
import com.example.nguyenthanhtungh.musicapplication.data.source.SongDataSource;
import com.example.nguyenthanhtungh.musicapplication.data.source.local.SongLocalDataSource;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class SongRepository implements SongDataSource.LocalDataSource,
        SongDataSource.RemoteDataSource {
    private static SongRepository sInstance;
    private SongLocalDataSource mSongLocalDataSource;

    @SuppressLint("RestrictedApi")
    private SongRepository(SongLocalDataSource songLocalDataSource) {
        mSongLocalDataSource = checkNotNull(songLocalDataSource);
    }

    @SuppressLint("RestrictedApi")
    public static SongRepository getInstance(SongLocalDataSource songLocalDataSource) {
        if (sInstance == null) {
            sInstance = new SongRepository(checkNotNull(songLocalDataSource));
        }
        return sInstance;
    }

    @Override
    public ArrayList<Song> getListSong(onFetchDataListener onFetchDataListener) {
        return mSongLocalDataSource.getListSong(onFetchDataListener);
    }
}
