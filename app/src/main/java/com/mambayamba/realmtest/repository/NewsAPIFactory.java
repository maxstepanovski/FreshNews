package com.mambayamba.realmtest.repository;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mambayamba.realmtest.R;
import com.mambayamba.realmtest.model.ArticleContainer;
import com.mambayamba.realmtest.model.Source;
import com.mambayamba.realmtest.model.SourceContainer;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by макс on 19.07.2017.
 */


public class NewsAPIFactory {
    private static final String BASE_URL = "https://newsapi.org/v1/";
    private static final String API_KEY = "4a63c54658e74283b344a21609a48064";
    private NewsAPIService service;
    private Retrofit retrofit;

    public NewsAPIFactory() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(NewsAPIService.class);
    }

    public Observable<SourceContainer> fetchSources(String category){
        return service.getSourcesByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ArticleContainer> fetchArticles(Source source){
        return service.getArticles(source.getId(), API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArticleContainer> fetchArticles(String sourceId){
        return service.getArticles(sourceId, API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
