package com.ogaclejapan.qiitanium.domain.model;

import com.ogaclejapan.qiitanium.Qiitanium;
import com.ogaclejapan.qiitanium.domain.core.EntityFactory;
import com.ogaclejapan.qiitanium.domain.core.Identifier;
import com.ogaclejapan.qiitanium.domain.usecase.FindTags;
import com.ogaclejapan.rx.binding.RxUtils;

import android.content.Context;

import java.util.List;
import java.util.WeakHashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Tags {

    private static final WeakHashMap<String, Tag> sInstances = new WeakHashMap<>();

    public static Tags with(final Context context) {
        final Tags instance = new Tags();
        Qiitanium.appComponent(context).injectDomain(instance);
        return instance;
    }

    public static Tag get(String id) {
        return sInstances.get(id);
    }

    private static void put(String id, Tag entity) {
        sInstances.put(id, entity);
    }

    public static class Factory extends EntityFactory<Identifier.WithT0, Tag> {

        public Factory(final Context context) {
            super(context);
        }

        @Override
        protected Tag newInstance(final Identifier.WithT0 idWithT) {
            Tag entity = get(idWithT.id);
            if (entity == null) {
                entity = new Tag(getContext(), idWithT.id);
                put(idWithT.id, entity);
            }
            return entity;
        }

    }

    @Inject
    FindTags mFindTags;


    public Observable<List<Tag>> list(Page page) {
        return RxUtils.toObservable(mFindTags, page).subscribeOn(Schedulers.io());
    }

}
