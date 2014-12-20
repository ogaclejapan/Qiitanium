package com.ogaclejapan.qiitanium.domain.usecase;

import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.model.Tag;
import com.ogaclejapan.qiitanium.domain.repository.TagRepository;

import java.util.List;

import rx.functions.Func1;

public class FindTags implements Func1<Page, List<Tag>> {

    private final TagRepository mRepository;

    public FindTags(TagRepository repository) {
        mRepository = repository;
    }

    @Override
    public List<Tag> call(final Page page) {
        return mRepository.findAll(page);
    }

}
