package com.example.nguyenthanhtungh.musicapplication.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.nguyenthanhtungh.musicapplication.data.model.Song;
import com.example.nguyenthanhtungh.musicapplication.data.source.SongDataSource;

import java.util.ArrayList;

public class SongLocalDataSource implements SongDataSource.LocalDataSource {
    private static SongLocalDataSource sInstance;
    private Context mContext;

    private SongLocalDataSource(Context context) {
        mContext = context;
    }

    public static SongLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SongLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public ArrayList<Song> getListSong(onFetchDataListener onFetchDataListener) {
        ArrayList<Song> songs = new ArrayList<>();
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String projections[] = new String[]{
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.DURATION
        };
        Cursor cursor = mContext.getContentResolver().query(audioUri, projections,
                null, null, null);
        cursor.moveToFirst();
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);

        String name, path, album, artist;
        int duration;
        while (!cursor.isAfterLast()) {
            name = cursor.getString(indexTitle);
            path = cursor.getString(indexData);
            album = cursor.getString(indexAlbum);
            artist = cursor.getString(indexArtist);
            duration = cursor.getInt(indexDuration);
            songs.add(new Song(name, path, album, artist, duration));
            cursor.moveToNext();
        }
        cursor.close();
        if (songs.size() > 0) {
            onFetchDataListener.onSuccess(songs);
        } else {
            onFetchDataListener.onError();
        }
        return songs;
    }
}
