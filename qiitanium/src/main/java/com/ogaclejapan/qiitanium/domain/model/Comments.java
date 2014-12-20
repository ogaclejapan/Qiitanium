package com.ogaclejapan.qiitanium.domain.model;

import com.ogaclejapan.qiitanium.Qiitanium;
import com.ogaclejapan.qiitanium.domain.core.EntityFactory;
import com.ogaclejapan.qiitanium.domain.core.Identifier;
import com.ogaclejapan.qiitanium.domain.usecase.FindCommentsInArticle;
import com.ogaclejapan.rx.binding.RxUtils;

import android.content.Context;

import java.util.List;
import java.util.WeakHashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;


public class Comments {

    private static final WeakHashMap<String, Comment> sInstances = new WeakHashMap<>();

    public static Comments with(final Context context) {
        return Qiitanium.from(context).graph().get(Comments.class);
    }

    public static Comment get(String id) {
        return sInstances.get(id);
    }

    private static void put(String id, Comment entity) {
        sInstances.put(id, entity);
    }

    public static class Factory extends EntityFactory<Identifier.WithT1<User>, Comment> {

        public Factory(final Context context) {
            super(context);
        }

        @Override
        protected Comment newInstance(final Identifier.WithT1<User> idWithT) {
            Comment entity = get(idWithT.id);
            if (entity == null) {
                entity = new Comment(getContext(), idWithT.id, idWithT.t1);
                put(idWithT.id, entity);
            }
            return entity;
        }

    }

    @Inject
    FindCommentsInArticle mListCommentByArticle;


    public Observable<List<Comment>> loadAllByArticle(final String articleId, final Page page) {
        return RxUtils.toObservable(mListCommentByArticle, articleId, page)
                .subscribeOn(Schedulers.io());
    }

}
