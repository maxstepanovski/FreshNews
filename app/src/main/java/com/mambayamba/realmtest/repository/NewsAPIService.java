package com.mambayamba.realmtest.repository;

import com.mambayamba.realmtest.model.ArticleContainer;
import com.mambayamba.realmtest.model.SourceContainer;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by макс on 19.07.2017.
 */

public interface NewsAPIService {
    @GET("sources?language=en")
    public Observable<SourceContainer> getSourcesByCategory(@Query("category") @Nullable String category);
    @GET("articles?")
    public Observable<ArticleContainer> getArticles(@Query("source") String source,
                                                    @Query("apiKey") String apiKey);
}
