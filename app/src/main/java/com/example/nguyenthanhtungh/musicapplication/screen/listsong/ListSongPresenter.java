package com.example.nguyenthanhtungh.musicapplication.screen.listsong;

import android.annotation.SuppressLint;

import com.example.nguyenthanhtungh.musicapplication.data.model.Song;
import com.example.nguyenthanhtungh.musicapplication.data.repository.SongRepository;
import com.example.nguyenthanhtungh.musicapplication.data.source.SongDataSource;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class ListSongPresenter implements ListSongContract.Presenter {
    private ListSongContract.View mView;
    private SongRepository mRepository;

    @SuppressLint("RestrictedApi")
    public ListSongPresenter(SongRepository repository) {
        mRepository = checkNotNull(repository);
    }

    public void setView(ListSongContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void getSong() {
        mRepository.getListSong(new SongDataSource.LocalDataSource.onFetchDataListener() {
            @Override
            public boolean onSuccess(ArrayList<Song> songs) {
                mView.displayListSong(songs);
                return true;
            }

            @Override
            public boolean onError() {
                return true;
            }
        });
    }
}
