package com.ogaclejapan.qiitanium.datasource.web.dto.mapper;

import android.content.Context;

import com.ogaclejapan.qiitanium.datasource.web.dto.ArticleDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.ArticleTagDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.AuthorDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.CommentAuthorDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.CommentDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.TagDto;
import com.ogaclejapan.qiitanium.domain.core.EntityMapper;
import com.ogaclejapan.qiitanium.domain.core.Identifier;
import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Articles;
import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.qiitanium.domain.model.Comments;
import com.ogaclejapan.qiitanium.domain.model.Tag;
import com.ogaclejapan.qiitanium.domain.model.Tags;
import com.ogaclejapan.qiitanium.domain.model.User;
import com.ogaclejapan.qiitanium.domain.model.Users;
import com.ogaclejapan.qiitanium.util.DateTimeUtils;
import com.ogaclejapan.qiitanium.util.NetUtils;
import com.ogaclejapan.qiitanium.util.StringUtils;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;

public class DtoToEntity {

  private static final String QIITA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final String GITHUB_ID_SUFFIX = "@github";
  private static final int EXCERPT_LENGTH = 150;

  public static class ArticleMapper extends EntityMapper<ArticleDto, Article> {

    private final Articles.Factory factory;
    private final AuthorMapper authorMapper;
    private final ArticleTagMapper articleTagMapper;
    private final SimpleDateFormat timeFormat;

    public ArticleMapper(final Context context) {
      super();
      this.factory = new Articles.Factory(context);
      this.authorMapper = new AuthorMapper(context);
      this.articleTagMapper = new ArticleTagMapper(context);
      this.timeFormat = new SimpleDateFormat(QIITA_TIME_FORMAT);
    }

    @Override
    public Article map(final ArticleDto dto) {
      final User author = authorMapper.map(dto.user);
      final Article entity = factory.create(Identifier.create(dto.uuid, author));
      entity.title.set(NetUtils.unescapeHtml(dto.title));
      entity.excerpt.set(StringUtils.excerpt(Jsoup.parse(dto.body).text(), EXCERPT_LENGTH));
      entity.content.set(dto.body);
      entity.createdAt.set(DateTimeUtils.parse(timeFormat, dto.createdAt));
      entity.commentCount.set(dto.commentCount);
      entity.stockCount.set(dto.stockCount);
      for (Tag tag : articleTagMapper.map(dto.tags)) {
        if (!entity.tags.contains(tag)) {
          entity.tags.add(tag);
        }
      }
      return entity;
    }

  }

  public static class ArticleTagMapper extends EntityMapper<ArticleTagDto, Tag> {

    private final Tags.Factory factory;

    public ArticleTagMapper(final Context context) {
      super();
      this.factory = new Tags.Factory(context);
    }

    @Override
    public Tag map(final ArticleTagDto dto) {
      final Tag entity = factory.create(Identifier.create(dto.urlName));
      entity.name.set(dto.name);
      return entity;
    }
  }

  public static class TagMapper extends EntityMapper<TagDto, Tag> {

    private final Tags.Factory factory;

    public TagMapper(final Context context) {
      super();
      this.factory = new Tags.Factory(context);
    }

    @Override
    public Tag map(final TagDto dto) {
      final Tag entity = factory.create(Identifier.create(NetUtils.encodeURL(dto.id)));
      entity.name.set(dto.id);
      return entity;
    }

  }

  public static class AuthorMapper extends EntityMapper<AuthorDto, User> {

    private final Users.Factory factory;

    public AuthorMapper(final Context context) {
      super();
      this.factory = new Users.Factory(context);
    }

    @Override
    public User map(final AuthorDto dto) {
      final User entity = factory.create(Identifier.create(dto.urlName));
      entity.name.set(NetUtils.decodeURL(dto.urlName).replace(GITHUB_ID_SUFFIX, ""));
      entity.thumbnailUrl.set(dto.profileImageUrl);
      return entity;
    }

  }

  public static class CommentMapper extends EntityMapper<CommentDto, Comment> {

    private final Comments.Factory factory;
    private final CommentAuthorMapper authorMapper;

    public CommentMapper(final Context context) {
      super();
      this.factory = new Comments.Factory(context);
      this.authorMapper = new CommentAuthorMapper(context);
    }

    @Override
    public Comment map(final CommentDto dto) {
      final User author = authorMapper.map(dto.user);
      final Comment entity = factory.create(Identifier.create(dto.id, author));
      entity.text.set(dto.body);
      entity.createdAt.set(DateTimeUtils.parse3339(dto.createdAt));
      return entity;
    }

  }

  public static class CommentAuthorMapper extends EntityMapper<CommentAuthorDto, User> {

    private final Users.Factory factory;

    public CommentAuthorMapper(final Context context) {
      super();
      this.factory = new Users.Factory(context);
    }

    @Override
    public User map(final CommentAuthorDto dto) {
      final User entity = factory.create(Identifier.create(dto.id));
      entity.name.set(dto.name);
      entity.thumbnailUrl.set(dto.profileImageUrl);
      return entity;
    }
  }

}
