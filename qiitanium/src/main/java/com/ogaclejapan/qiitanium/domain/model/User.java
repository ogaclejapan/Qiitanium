package com.ogaclejapan.qiitanium.domain.model;

import android.content.Context;

import com.ogaclejapan.qiitanium.domain.core.EntityModel;
import com.ogaclejapan.rx.binding.RxProperty;

public class User extends EntityModel {

  private static final String QIITA_URL_FORMAT = "https://qiita.com/%s";

  public final RxProperty<String> name = RxProperty.create();
  public final RxProperty<String> thumbnailUrl = RxProperty.create();

  User(Context context, String id) {
    super(context, id);
  }

  public String url() {
    return String.format(QIITA_URL_FORMAT, id);
  }

}
