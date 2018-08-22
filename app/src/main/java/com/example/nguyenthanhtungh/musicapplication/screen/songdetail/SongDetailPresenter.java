package com.example.nguyenthanhtungh.musicapplication.screen.songdetail;

import android.annotation.SuppressLint;

import com.example.nguyenthanhtungh.musicapplication.data.model.Song;
import com.example.nguyenthanhtungh.musicapplication.data.repository.SongRepository;
import com.example.nguyenthanhtungh.musicapplication.data.source.SongDataSource;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class SongDetailPresenter implements SongDetailContract.Presenter {
    private SongRepository mSongRepository;
    private SongDetailContract.View mView;

    @SuppressLint("RestrictedApi")
    public SongDetailPresenter(SongRepository songRepository) {
        mSongRepository = checkNotNull(songRepository);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getSong() {
        mSongRepository.getListSong(new SongDataSource.LocalDataSource.onFetchDataListener() {
            @Override
            public boolean onSuccess(ArrayList<Song> songs) {
                mView.displaySong(songs);
                return true;
            }

            @Override
            public boolean onError() {
                return true;
            }
        });
    }

    public void setView(SongDetailContract.View view) {
        mView = view;
    }

    @Override
    public void onStop() {

    }
}
