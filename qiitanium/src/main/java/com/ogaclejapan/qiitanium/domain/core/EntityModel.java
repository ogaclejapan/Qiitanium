package com.ogaclejapan.qiitanium.domain.core;

import android.content.Context;

public abstract class EntityModel extends Entity {

  private final Context context;

  protected EntityModel(Context context, String id) {
    super(id);
    this.context = context;
  }

  public Context getContext() {
    return context;
  }

}
