package com.ogaclejapan.qiitanium.domain.model;

import android.content.Context;

import com.ogaclejapan.qiitanium.Qiitanium;
import com.ogaclejapan.qiitanium.domain.core.EntityFactory;
import com.ogaclejapan.qiitanium.domain.core.Identifier;
import com.ogaclejapan.qiitanium.domain.usecase.FindCommentsInArticle;
import com.ogaclejapan.rx.binding.RxUtils;

import java.util.List;
import java.util.WeakHashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Comments {

  private static final WeakHashMap<String, Comment> instances = new WeakHashMap<>();

  @Inject FindCommentsInArticle findCommentsByArticle;

  public static Comments with(final Context context) {
    final Comments instance = new Comments();
    Qiitanium.appComponent(context).injectDomain(instance);
    return instance;
  }

  public static Comment get(String id) {
    return instances.get(id);
  }

  private static void put(String id, Comment entity) {
    instances.put(id, entity);
  }

  public Observable<List<Comment>> loadAllByArticle(final String articleId, final Page page) {
    return RxUtils.toObservable(findCommentsByArticle, articleId, page)
        .subscribeOn(Schedulers.io());
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

}
