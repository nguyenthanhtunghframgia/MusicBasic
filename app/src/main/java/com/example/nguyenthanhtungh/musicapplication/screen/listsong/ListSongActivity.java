package com.example.nguyenthanhtungh.musicapplication.screen.listsong;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nguyenthanhtungh.musicapplication.R;
import com.example.nguyenthanhtungh.musicapplication.data.model.Song;
import com.example.nguyenthanhtungh.musicapplication.data.repository.SongRepository;
import com.example.nguyenthanhtungh.musicapplication.data.source.local.SongLocalDataSource;
import com.example.nguyenthanhtungh.musicapplication.screen.songdetail.SongDetailActivity;
import com.example.nguyenthanhtungh.musicapplication.util.Constants;

import java.util.ArrayList;

public class ListSongActivity extends AppCompatActivity implements
        ListSongContract.View, OnRecyclerItemClickListener {
    private ListSongContract.Presenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
    }

    private void initData() {
        mPresenter = new ListSongPresenter(SongRepository.getInstance(
                SongLocalDataSource.getInstance(this)));
        mPresenter.setView(this);
        mPresenter.getSong();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    private void initPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void displayListSong(ArrayList<Song> songs) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        SongAdapter songAdapter = new SongAdapter(songs);
        songAdapter.setOnRecyclerItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(songAdapter);
    }


    @Override
    public void onItemClick(int position) {
        int i = position;
        Intent intent = new Intent(this, SongDetailActivity.class);
        intent.putExtra(Constants.KEY_SEND_SONG_POSITION, position);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }
}

