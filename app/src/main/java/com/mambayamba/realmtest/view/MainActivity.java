package com.mambayamba.realmtest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mambayamba.realmtest.R;
import com.mambayamba.realmtest.model.Article;
import com.mambayamba.realmtest.model.ArticleContainer;
import com.mambayamba.realmtest.model.RealmArticle;
import com.mambayamba.realmtest.model.RealmSource;
import com.mambayamba.realmtest.model.Source;
import com.mambayamba.realmtest.model.SourceCategories;
import com.mambayamba.realmtest.model.SourceContainer;
import com.mambayamba.realmtest.repository.NewsAPIFactory;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import me.tatarka.rxloader.RxLoader;
import me.tatarka.rxloader.RxLoaderManager;

public class MainActivity extends AppCompatActivity {
    private RxLoader<SourceContainer> rxLoader;
    private RxLoaderManager rxManager;
    private Realm realmUi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        realmUi = Realm.getDefaultInstance();
        NewsAPIFactory factory = new NewsAPIFactory();

        //firstly fetching data from database -> wrapping it in observable
        RealmResults<RealmSource> cachedResults = realmUi
                .where(RealmSource.class)
                .equalTo("category", SourceCategories.BUSINESS)
                .findAll();
        List<RealmSource> data = new ArrayList<>();
        data.addAll(cachedResults);
        Observable<RealmSource> cachedData = Observable.fromIterable(data);
        cachedData.flatMap(s->Observable.just(s));

        //secondly fetching data from web -> also wrapping it into observable
        // + merging with cached data from data base
        Observable<SourceContainer> observable = factory.fetchSources(SourceCategories.BUSINESS);
        observable
                .flatMap(a->Observable.fromIterable(a.getSources()))
                .map(this::writeSourceToRealm)
                .map(this::readSourceFromRealm)
                .mergeWith(cachedData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmUi.close();
    }

    private RealmSource readSourceFromRealm(String id){
        return findSourceInRealm(realmUi, id);
    }

    private RealmSource findSourceInRealm(Realm realm, String id){
        return realm.where(RealmSource.class).equalTo("id", id).findFirst();
    }

    private String writeSourceToRealm(Source sourceResponse) {
        Realm realm = Realm.getDefaultInstance();
        final RealmSource realmSourceResponse = new RealmSource(sourceResponse);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm transactionRealm) {
                RealmSource sourceFromRealm = findSourceInRealm(transactionRealm, realmSourceResponse.getId());
                if (sourceFromRealm == null) {
                    transactionRealm.copyToRealm(realmSourceResponse);
                }
            }
        });
        realm.close();
        return realmSourceResponse.getId();
    }
}
