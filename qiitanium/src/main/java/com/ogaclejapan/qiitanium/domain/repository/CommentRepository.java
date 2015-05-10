package com.ogaclejapan.qiitanium.domain.repository;

import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.qiitanium.domain.model.Page;

import java.util.List;

public interface CommentRepository {

  List<Comment> findAll(String articleId, Page page) throws DataAccessException;

}
