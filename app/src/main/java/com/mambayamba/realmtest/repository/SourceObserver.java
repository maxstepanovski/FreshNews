package com.mambayamba.realmtest.repository;


import android.util.Log;

import com.mambayamba.realmtest.model.Source;
import com.mambayamba.realmtest.model.SourceContainer;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by макс on 19.07.2017.
 */

public class SourceObserver implements Observer<SourceContainer> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Log.d("happy", "subscribed!");
    }

    @Override
    public void onNext(@NonNull SourceContainer resourceContainer) {
        List<Source> sources = resourceContainer.getSources();
        for(Source source: sources){
            Log.d("happy", source.getName());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("source downloading error", e.toString());
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        Log.d("happy", "completed!");
    }
}
