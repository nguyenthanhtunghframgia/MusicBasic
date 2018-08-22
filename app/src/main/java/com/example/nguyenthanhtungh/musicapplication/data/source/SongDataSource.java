package com.example.nguyenthanhtungh.musicapplication.data.source;

import com.example.nguyenthanhtungh.musicapplication.data.model.Song;

import java.util.ArrayList;

public interface SongDataSource {

    interface LocalDataSource {
        ArrayList<Song> getListSong(onFetchDataListener onFetchDataListener);

        interface onFetchDataListener {
            boolean onSuccess(ArrayList<Song> songs);

            boolean onError();
        }
    }

    interface RemoteDataSource {

    }
}
