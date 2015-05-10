package com.ogaclejapan.qiitanium.datasource.repository;

import android.content.Context;

import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV2;
import com.ogaclejapan.qiitanium.datasource.web.dto.CommentDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.ResponseDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.mapper.DtoToEntity;
import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.repository.CommentRepository;

import java.util.List;

public class CommentDataRepository implements CommentRepository {

  private final Context context;
  private final QiitaApiV2 api;

  public CommentDataRepository(Context context, QiitaApiV2 api) {
    this.context = context;
    this.api = api;
  }

  @Override
  public List<Comment> findAll(String articleId, Page page)
      throws DataAccessException {
    final int pageNum = page.getAndIncrement();
    final ResponseDto<List<CommentDto>> response = api.getComments(articleId, pageNum);
    page.setLast(response.page.last);
    return parseComments(response.body);
  }

  private List<Comment> parseComments(List<CommentDto> dtos) {
    return new DtoToEntity.CommentMapper(context).map(dtos);
  }

}
