package com.ogaclejapan.qiitanium.domain.core;

import android.content.Context;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class EntityFactory<T extends Identifier, R extends Entity> {

  private final Lock lock = new ReentrantLock();
  private final Context context;

  protected EntityFactory(Context context) {
    this.context = context;
  }

  public R create(T idWithT) {
    lock.lock();
    try {
      return newInstance(idWithT);
    } finally {
      lock.unlock();
    }
  }

  protected abstract R newInstance(T idWithT);

  protected Context getContext() {
    return context;
  }
}
