package com.example.nguyenthanhtungh.musicapplication.screen.songdetail;

import com.example.nguyenthanhtungh.musicapplication.data.model.Song;
import com.example.nguyenthanhtungh.musicapplication.screen.base.BasePresenter;

import java.util.ArrayList;

public interface SongDetailContract {

    interface View {
        void displaySong(ArrayList<Song> songs);
    }

    interface Presenter extends BasePresenter<View> {
        void getSong();
    }
}
