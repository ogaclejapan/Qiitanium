package com.ogaclejapan.qiitanium.domain.model;

import android.content.Context;

import com.ogaclejapan.qiitanium.domain.core.EntityModel;
import com.ogaclejapan.rx.binding.RxProperty;

public class Tag extends EntityModel {

  public final RxProperty<String> name = RxProperty.create();
  public final RxProperty<Boolean> followed = RxProperty.of(false);

  Tag(Context context, String id) {
    super(context, id);
  }

}
