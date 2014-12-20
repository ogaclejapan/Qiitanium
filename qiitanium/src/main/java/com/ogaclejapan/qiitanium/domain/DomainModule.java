package com.ogaclejapan.qiitanium.domain;

import com.ogaclejapan.qiitanium.domain.model.Articles;
import com.ogaclejapan.qiitanium.domain.model.Comments;
import com.ogaclejapan.qiitanium.domain.model.Tags;
import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;
import com.ogaclejapan.qiitanium.domain.repository.CommentRepository;
import com.ogaclejapan.qiitanium.domain.repository.TagRepository;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticleById;
import com.ogaclejapan.qiitanium.domain.usecase.FindCommentsInArticle;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticlesByTag;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticles;
import com.ogaclejapan.qiitanium.domain.usecase.FindTags;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                Articles.class,
                Comments.class,
                Tags.class
        },
        complete = false, library = true)
public class DomainModule {

    @Provides
    @Singleton
    FindArticleById provideFindArticleById(ArticleRepository repository) {
        return new FindArticleById(repository);
    }

    @Provides
    @Singleton
    FindArticles provideFindArticle(ArticleRepository repository) {
        return new FindArticles(repository);
    }

    @Provides
    @Singleton
    FindArticlesByTag provideFindArticlesByTag(ArticleRepository repository) {
        return new FindArticlesByTag(repository);
    }

    @Provides
    @Singleton
    FindCommentsInArticle provideFindCommentsInArticle(CommentRepository repository) {
        return new FindCommentsInArticle(repository);
    }

    @Provides
    @Singleton
    FindTags provideFindTags(TagRepository repository) {
        return new FindTags(repository);
    }


}
