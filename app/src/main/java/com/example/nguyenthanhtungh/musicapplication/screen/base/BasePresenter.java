package com.example.nguyenthanhtungh.musicapplication.screen.base;

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
