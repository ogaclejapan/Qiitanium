package com.ogaclejapan.qiitanium.domain.model;

import com.ogaclejapan.qiitanium.domain.core.EntityModel;
import com.ogaclejapan.rx.binding.RxProperty;

import android.content.Context;

import java.util.Date;

public class Comment extends EntityModel {

    public final RxProperty<String> text = RxProperty.create();
    public final RxProperty<Date> createdAt = RxProperty.create();
    public final User author;

    Comment(Context context, String id, User author) {
        super(context, id);
        this.author = author;
    }


}
