package com.ogaclejapan.qiitanium.domain.core;

import android.content.Context;

public abstract class EntityModel extends Entity {

    private final Context mContext;

    protected EntityModel(Context context, String id) {
        super(id);
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

}
