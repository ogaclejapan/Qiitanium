package com.ogaclejapan.qiitanium.util;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
import rx.functions.Func1;

public final class Objects {

  private Objects() {}

  public static void assertNotNull(Object obj) {
    if (obj == null) {
      throw new IllegalArgumentException("Must be not null.");
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T cast(Object obj) {
    return (T) obj;
  }

  public static <T, R> List<R> map(List<T> sources, Func1<T, R> mapper) {
    return map(sources, mapper, null);
  }

  public static <T, R> List<R> map(List<T> sources, Func1<T, R> mapper, Action1<R> injection) {
    assertNotNull(mapper);
    if (sources == null) {
      return null;
    }
    final List<R> mappedList = new ArrayList<>(sources.size());
    for (T source : sources) {
      final R dst = mapper.call(source);
      if (injection != null) {
        injection.call(dst);
      }
      mappedList.add(dst);
    }
    return mappedList;
  }

}
