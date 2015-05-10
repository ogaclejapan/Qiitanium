package com.ogaclejapan.qiitanium.domain.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Page extends AtomicInteger {

  private static final int MIN_PAGE_NUM = 1;

  private final AtomicBoolean isLast = new AtomicBoolean(false);

  private Page(final int initialValue) {
    super(initialValue);
  }

  public static Page create() {
    return new Page(MIN_PAGE_NUM);
  }

  public boolean isLast() {
    return isLast.get();
  }

  public void setLast(int num) {
    isLast.set((get() > num));
  }

  public void reset() {
    set(MIN_PAGE_NUM);
    isLast.set(false);
  }

}
