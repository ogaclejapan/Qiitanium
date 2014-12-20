package com.ogaclejapan.qiitanium.domain.repository;

import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.qiitanium.domain.model.Page;

import java.util.List;

public interface ArticleRepository {

    Article findById(String articleId) throws DataAccessException;

    List<Article> findAll(Page page) throws DataAccessException;

    List<Article> findAllByTag(String tagId, Page page) throws DataAccessException;

}
