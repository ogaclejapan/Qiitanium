package com.ogaclejapan.qiitanium.domain.model;

import com.ogaclejapan.qiitanium.domain.core.EntityModel;
import com.ogaclejapan.rx.binding.RxProperty;

import android.content.Context;

public class User extends EntityModel {

    public final RxProperty<String> name = RxProperty.create();
    public final RxProperty<String> thumbnailUrl = RxProperty.create();

    User(Context context, String id) {
        super(context, id);
    }

}
