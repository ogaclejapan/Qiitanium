package com.ogaclejapan.qiitanium.domain.model;

import com.ogaclejapan.qiitanium.Qiitanium;
import com.ogaclejapan.qiitanium.domain.core.EntityFactory;
import com.ogaclejapan.qiitanium.domain.core.Identifier;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticleById;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticles;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticlesByTag;
import com.ogaclejapan.rx.binding.RxUtils;

import android.content.Context;

import java.util.List;
import java.util.WeakHashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Articles {

    private static final WeakHashMap<String, Article> sInstances = new WeakHashMap<>();

    public static Articles with(Context context) {
        Articles instance = new Articles();
        Qiitanium.appComponent(context).injectDomain(instance);
        return instance;
    }

    public static Article get(String id) {
        return sInstances.get(id);
    }

    private static void put(String id, Article entity) {
        sInstances.put(id, entity);
    }

    public static class Factory extends EntityFactory<Identifier.WithT1<User>, Article> {

        public Factory(Context context) {
            super(context);
        }

        @Override
        protected Article newInstance(Identifier.WithT1<User> idWithT) {
            Article entity = get(idWithT.id);
            if (entity == null) {
                entity = new Article(getContext(), idWithT.id, idWithT.t1);
                put(idWithT.id, entity);
            }
            return entity;
        }

    }

    @Inject
    FindArticleById findArticleById;

    @Inject
    FindArticles mFindArticles;

    @Inject
    FindArticlesByTag mFindArticlesByTag;


    public Observable<Article> loadById(String articleId) {
        Article cachedArticle = get(articleId);
        if (cachedArticle != null) {
            return Observable.just(cachedArticle);
        } else {
            return RxUtils.toObservable(findArticleById, articleId).subscribeOn(Schedulers.io());
        }
    }

    public Observable<List<Article>> loadAll(Page page) {
        return RxUtils.toObservable(mFindArticles, page).subscribeOn(Schedulers.io());
    }

    public Observable<List<Article>> loadAllByTag(String tagId, Page page) {
        return RxUtils.toObservable(mFindArticlesByTag, tagId, page).subscribeOn(Schedulers.io());
    }

}
