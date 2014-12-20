package com.ogaclejapan.qiitanium.domain.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Page extends AtomicInteger {

    private static final int MIN_PAGE_NUM = 1;

    private final AtomicBoolean mIsLast = new AtomicBoolean(false);

    private Page(final int initialValue) {
        super(initialValue);
    }

    public static Page create() {
        return new Page(MIN_PAGE_NUM);
    }

    public boolean isLast() {
        return mIsLast.get();
    }

    public void setLast(int num) {
        mIsLast.set((get() > num));
    }

    public void reset() {
        set(MIN_PAGE_NUM);
        mIsLast.set(false);
    }

}
