package com.ogaclejapan.qiitanium.datasource.repository;

import android.content.Context;

import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV2;
import com.ogaclejapan.qiitanium.datasource.web.dto.ResponseDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.TagDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.mapper.DtoToEntity;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.model.Tag;
import com.ogaclejapan.qiitanium.domain.repository.TagRepository;

import java.util.List;

public class TagDataRepository implements TagRepository {

  private final Context context;
  private final QiitaApiV2 api;

  public TagDataRepository(Context context, QiitaApiV2 api) {
    this.context = context;
    this.api = api;
  }

  @Override
  public List<Tag> findAll(Page page) {
    final int pageNum = page.getAndIncrement();
    final ResponseDto<List<TagDto>> response = api.getTags(pageNum);
    page.setLast(response.page.last);
    return parseTags(response.body);
  }

  private List<Tag> parseTags(List<TagDto> dtos) {
    return new DtoToEntity.TagMapper(context).map(dtos);
  }

}
