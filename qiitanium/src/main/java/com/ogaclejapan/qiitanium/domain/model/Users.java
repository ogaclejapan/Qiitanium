package com.ogaclejapan.qiitanium.domain.model;

import com.ogaclejapan.qiitanium.Qiitanium;
import com.ogaclejapan.qiitanium.domain.core.EntityFactory;
import com.ogaclejapan.qiitanium.domain.core.Identifier;

import android.content.Context;

import java.util.WeakHashMap;

public class Users {

    private static final WeakHashMap<String, User> sInstances = new WeakHashMap<>();

    public static Users with(final Context context) {
        final Users instance = new Users();
        Qiitanium.appComponent(context).injectDomain(instance);
        return instance;
    }

    public static User get(final String id) {
        return sInstances.get(id);
    }

    private static void put(final String id, final User entity) {
        sInstances.put(id, entity);
    }

    public static class Factory extends EntityFactory<Identifier.WithT0, User> {

        public Factory(final Context context) {
            super(context);
        }

        @Override
        protected User newInstance(final Identifier.WithT0 idWithT) {
            User entity = get(idWithT.id);
            if (entity == null) {
                entity = new User(getContext(), idWithT.id);
                put(idWithT.id, entity);
            }
            return entity;
        }

    }


}
