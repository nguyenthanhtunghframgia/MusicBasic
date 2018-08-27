package com.example.nguyenthanhtungh.musicapplication.screen.listsong;

import com.example.nguyenthanhtungh.musicapplication.data.model.Song;
import com.example.nguyenthanhtungh.musicapplication.screen.base.BasePresenter;

import java.util.ArrayList;

interface ListSongContract {

    interface View {
        void displayListSong(ArrayList<Song> songs);
    }

    interface Presenter extends BasePresenter<View> {
        void getSong();
    }
}
