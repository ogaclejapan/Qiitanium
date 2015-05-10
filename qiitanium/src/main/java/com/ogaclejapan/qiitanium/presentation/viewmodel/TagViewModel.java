package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Tag;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

public class TagViewModel extends AppViewModel {

  private final Tag tag;

  protected TagViewModel(Tag tag) {
    this.tag = tag;
  }

  public static TagViewModel create(final Tag tag) {
    return new TagViewModel(tag);
  }

  public String id() {
    return tag.id;
  }

  public RxReadOnlyProperty<String> name() {
    return tag.name;
  }

  public static class Mapper extends AppModelMapper<Tag, TagViewModel> {

    @Override
    public TagViewModel call(final Tag model) {
      return create(model);
    }

  }
}
