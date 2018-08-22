package com.example.nguyenthanhtungh.musicapplication.screen.songdetail;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.nguyenthanhtungh.musicapplication.data.model.Song;

import java.io.IOException;

public class SongService extends Service {
    private MediaPlayer mPlayer;
    public IBinder binder = new MyServiceBinder();
    private boolean isResource;

    class MyServiceBinder extends Binder {
        public SongService getSongService() {
            return SongService.this;
        }
    }

    @Override
    public void onCreate() {
        mPlayer = new MediaPlayer();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void play(Song song) {
        try {
            if (!isResource) {
                mPlayer.reset();
                mPlayer.setDataSource(song.getPath());
                mPlayer.prepare();
                mPlayer.start();
                isResource = true;
            } else {
                mPlayer.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mPlayer.pause();
    }

    public long getCurrentTime() {
        return mPlayer.getCurrentPosition();
    }

    public void seek(int progress) {
        mPlayer.seekTo(progress);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
