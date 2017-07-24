package com.mambayamba.realmtest.repository;

import android.util.Log;

import com.mambayamba.realmtest.model.Article;
import com.mambayamba.realmtest.model.ArticleContainer;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by макс on 19.07.2017.
 */

public class ArticleObserver implements Observer<ArticleContainer> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull ArticleContainer articleContainer) {
        List<Article> articles = articleContainer.getArticles();
        for(Article article: articles){
            Log.d("happy", article.getTitle());
    }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.d("happy", "article downloading error");
    }

    @Override
    public void onComplete() {

    }
}
