package com.ogaclejapan.qiitanium.domain.core;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public abstract class EntityMapper<T, R extends Entity> implements Func1<T, R> {

    protected EntityMapper() {
        //Do nothing.
    }

    public abstract R map(T source);

    public final List<R> map(List<T> sources) {
        if (sources == null) {
            return null;
        }
        final List<R> dst = new ArrayList<>();
        for (T source : sources) {
            dst.add(map(source));
        }
        return dst;
    }

    @Override
    public R call(final T t) {
        return map(t);
    }

}
