package com.example.nguyenthanhtungh.musicapplication.screen.listsong;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyenthanhtungh.musicapplication.R;
import com.example.nguyenthanhtungh.musicapplication.data.model.Song;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private ArrayList<Song> mSongs;

    public SongAdapter(ArrayList<Song> songs) {
        mSongs = songs;
    }

    public void setOnRecyclerItemClickListener(
            OnRecyclerItemClickListener onRecyclerItemClickListener) {
        mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_song, viewGroup, false);
        return new SongHolder(view, mOnRecyclerItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder songHolder, int i) {
        songHolder.bindData(mSongs.get(i));
    }

    @Override
    public int getItemCount() {
        return mSongs.size() == 0 ? 0 : mSongs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnRecyclerItemClickListener mOnRecyclerItemClickListener;
        private TextView mSongName;
        private TextView mSongArtist;

        public SongHolder(@NonNull View itemView,
                          OnRecyclerItemClickListener onRecyclerItemClickListener) {
            super(itemView);
            mOnRecyclerItemClickListener = onRecyclerItemClickListener;
            mSongName = itemView.findViewById(R.id.text_item_song_name);
            mSongArtist = itemView.findViewById(R.id.text_item_song_artist);
            itemView.setOnClickListener(this);
        }

        private void bindData(Song song) {
            mSongName.setText(song.getName());
            mSongArtist.setText(song.getArtist());
        }

        @Override
        public void onClick(View view) {
            mOnRecyclerItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
