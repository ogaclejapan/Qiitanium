package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Tag;
import com.ogaclejapan.rx.binding.RxEvent;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

public class TagViewModel extends AppViewModel {

    private final Tag mTag;

    protected TagViewModel(Tag tag) {
        mTag = tag;
    }

    public static TagViewModel create(final Tag tag) {
        return new TagViewModel(tag);
    }

    public String id() {
        return mTag.id;
    }

    public RxReadOnlyProperty<String> name() {
        return mTag.name;
    }

    public static class Mapper extends AppModelMapper<Tag, TagViewModel> {

        @Override
        public TagViewModel call(final Tag model) {
            return create(model);
        }

    }
}
