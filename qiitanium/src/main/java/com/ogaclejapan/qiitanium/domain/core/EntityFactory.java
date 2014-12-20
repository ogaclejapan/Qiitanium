package com.ogaclejapan.qiitanium.domain.core;

import android.content.Context;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class EntityFactory<T extends Identifier, R extends Entity> {

    private final Lock mLock = new ReentrantLock();
    private final Context mContext;

    protected EntityFactory(Context context) {
        mContext = context;
    }

    public R create(T idWithT) {
        mLock.lock();
        try {
            return newInstance(idWithT);
        } finally {
            mLock.unlock();
        }
    }

    protected abstract R newInstance(T idWithT);

    protected Context getContext() {
        return mContext;
    }
}
