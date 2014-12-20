package com.ogaclejapan.qiitanium.domain.usecase;

import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.repository.CommentRepository;

import java.util.List;

import rx.functions.Func2;

public class FindCommentsInArticle implements Func2<String, Page, List<Comment>> {

    private final CommentRepository mRepository;

    public FindCommentsInArticle(final CommentRepository repository) {
        mRepository = repository;
    }

    @Override
    public List<Comment> call(final String articleId, final Page page) {
        return mRepository.findAll(articleId, page);
    }

}
