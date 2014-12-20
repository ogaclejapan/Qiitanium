package com.ogaclejapan.qiitanium.domain.usecase;

import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;

import java.util.List;

import rx.functions.Func2;

public class FindArticlesByTag implements Func2<String, Page, List<Article>> {

    private final ArticleRepository mRepository;

    public FindArticlesByTag(ArticleRepository repository) {
        mRepository = repository;
    }

    @Override
    public List<Article> call(final String tagId, final Page page) {
        return mRepository.findAllByTag(tagId, page);
    }

}
