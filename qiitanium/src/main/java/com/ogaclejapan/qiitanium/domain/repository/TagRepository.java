package com.ogaclejapan.qiitanium.domain.repository;

import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.model.Tag;

import java.util.List;

public interface TagRepository {

    List<Tag> findAll(Page page) throws DataAccessException;

}
