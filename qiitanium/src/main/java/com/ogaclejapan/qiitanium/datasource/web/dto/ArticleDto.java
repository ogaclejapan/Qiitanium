package com.ogaclejapan.qiitanium.datasource.web.dto;

import java.util.List;

public class ArticleDto {

  public String uuid;

  public String title;

  public String body;

  public Integer commentCount;

  public Integer stockCount;

  public String createdAt;

  public String updatedAt;

  public List<ArticleTagDto> tags;

  public AuthorDto user;

}
