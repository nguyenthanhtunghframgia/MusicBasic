package com.example.nguyenthanhtungh.musicapplication.screen.songdetail;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nguyenthanhtungh.musicapplication.R;
import com.example.nguyenthanhtungh.musicapplication.data.model.Song;
import com.example.nguyenthanhtungh.musicapplication.data.repository.SongRepository;
import com.example.nguyenthanhtungh.musicapplication.data.source.local.SongLocalDataSource;
import com.example.nguyenthanhtungh.musicapplication.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SongDetailActivity extends AppCompatActivity implements SongDetailContract.View, SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private static final int NOTIFI_ID = 1;
    private SongDetailContract.Presenter mPresenter;
    private TextView mSongName;
    private TextView mSongArtist;
    private TextView mSongDuration;
    private TextView mSongCurrentDuration;
    private SeekBar mSeekBar;
    private ImageButton mPrevious;
    private ImageButton mPlayPause;
    private ImageButton mNext;
    private int mPosition;
    private ArrayList<Song> mSongs;
    private ImageView mImageSong;
    private SongService mSongService;
    private boolean isPlay;
    private boolean isBound;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initData();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intentService = new Intent(this, SongService.class);
        bindService(intentService, serviceConnection, BIND_AUTO_CREATE);
    }

    private void initData() {
        mPresenter = new SongDetailPresenter(SongRepository
                .getInstance(SongLocalDataSource.getInstance(this)));
        mPresenter.setView(this);
        mPresenter.getSong();
    }

    private void initViews() {
        mSongName = findViewById(R.id.text_song_name);
        mSongArtist = findViewById(R.id.text_song_artist);
        mSongDuration = findViewById(R.id.text_duration);
        mSongCurrentDuration = findViewById(R.id.text_current_time);
        mSeekBar = findViewById(R.id.seek_bar);
        mPrevious = findViewById(R.id.button_previous);
        mPlayPause = findViewById(R.id.button_play_pause);
        mNext = findViewById(R.id.button_next);
        mImageSong = findViewById(R.id.image_song);

        mSeekBar.setOnSeekBarChangeListener(this);
        mPrevious.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
        mNext.setOnClickListener(this);

        upDateSongInfo(mSongs.get(mPosition));
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SongService.MyServiceBinder binder = (SongService.MyServiceBinder) iBinder;
            mSongService = binder.getSongService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    public void upDateSongInfo(Song song) {
        mSongName.setText(song.getName());
        mSongArtist.setText(song.getArtist());
        mSongDuration.setText(getTimeText(song.getDuration()));
        mSeekBar.setMax(song.getDuration());
        new UpdateSeekBar().execute();
        showNotification(mSongs.get(mPosition));
    }


    public String getTimeText(long duration) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(new Date(duration));
    }

    @Override
    public void displaySong(ArrayList<Song> songs) {
        mPosition = getIntent().getIntExtra(Constants.KEY_SEND_SONG_POSITION, 0);
        mSongs = songs;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mSongService.seek(seekBar.getProgress());
    }

    private class UpdateSeekBar extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                try {
                    Thread.sleep(1000);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mSongCurrentDuration.setText(getTimeText(mSongService.getCurrentTime()));
            mSeekBar.setProgress((int) mSongService.getCurrentTime());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_previous:
                doPrevious();
                break;
            case R.id.button_play_pause:
                doPlayPause();
                break;
            case R.id.button_next:
                doNext();
                break;
            default:
                break;
        }
    }

    private void doPrevious() {
    }

    private void doNext() {
    }

    private void doPlayPause() {
        if (!isPlay) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely);
            mImageSong.setAnimation(animation);
            mImageSong.startAnimation(animation);
            isPlay = true;
            mPlayPause.setImageLevel(1);
            mSongService.play(mSongs.get(mPosition));
        } else {
            mImageSong.clearAnimation();
            isPlay = false;
            mPlayPause.setImageLevel(0);
            mSongService.pause();
        }
    }

    private void showNotification(Song song) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(this.getResources().getString(R.string.app_name));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(song.getName());
        builder.setContentText(song.getArtist());
        builder.setSubText(song.getAlbum());

        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFI_ID, builder.build());
    }

    @Override
    public void onBackPressed() {
        showNotification(mSongs.get(mPosition));
        super.onBackPressed();
    }
}
