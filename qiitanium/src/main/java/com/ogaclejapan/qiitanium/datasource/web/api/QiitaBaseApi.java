package com.ogaclejapan.qiitanium.datasource.web.api;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogaclejapan.qiitanium.datasource.exception.WebAccessException;
import com.ogaclejapan.qiitanium.datasource.web.dto.ErrorDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.PageDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.ResponseDto;
import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

abstract class QiitaBaseApi extends WebApi {

  protected static final String ENDPOINT = "https://qiita.com/api";
  protected static final String PARAM_KEY_PAGE = "page";
  protected static final String PARAM_KEY_PER_PAGE = "per_page";
  protected static final String PARAM_COUNT_PER_PAGE = "20";
  protected static final String RESPONSE_HEADER_LINK = "link";
  protected static final Pattern LINK_PATTERN = Pattern.compile(
      ".*" + Pattern.quote(ENDPOINT) + ".*\\?page=([1-9]*[0-9]).*>; rel=\"([a-z]+)\""
  );
  protected static final int LINK_MATCH_GROUP_PAGENUM = 1;
  protected static final int LINK_MATCH_GROUP_REL = 2;

  protected QiitaBaseApi(OkHttpClient okHttp, Gson gson) {
    super(okHttp, gson, ENDPOINT);
  }

  protected Uri.Builder url(int page, String path) {
    return url(path)
        .appendQueryParameter(PARAM_KEY_PAGE, String.valueOf(page))
        .appendQueryParameter(PARAM_KEY_PER_PAGE, PARAM_COUNT_PER_PAGE);
  }

  protected Uri.Builder url(int page, String path, Object... args) {
    return url(page, String.format(path, args));
  }

  protected <T> ResponseDto<T> execute(Request.Builder request, Class<T> classOfT) {
    return execute(request, TypeToken.get(classOfT));
  }

  protected <T> ResponseDto<T> execute(Request.Builder request,
      TypeToken<T> tokenOfT) {
    return execute(request, tokenOfT.getType());
  }

  protected <T> ResponseDto<T> execute(Request.Builder request, Type type) {
    try {
      final Response response = execute(request);
      if (!response.isSuccessful()) {
        throw new WebAccessException(response.code(), fetchErrorMessage(response));
      }

      final ResponseDto<T> dto = new ResponseDto<T>();
      dto.body = objectify(response, type);
      dto.page = fetchPagenationHeader(response);

      return dto;
    } catch (IOException ioe) {
      throw new DataAccessException(ioe);
    }
  }

  protected PageDto fetchPagenationHeader(Response response) {
    final PageDto page = new PageDto();
    final String linkHeader = response.header(RESPONSE_HEADER_LINK);
    if (TextUtils.isEmpty(linkHeader)) {
      return page;
    }

    final String[] links = linkHeader.split(",");
    for (String link : links) {
      Matcher m = LINK_PATTERN.matcher(link);
      while (m.find()) {
        final int pageNum = Integer.parseInt(m.group(LINK_MATCH_GROUP_PAGENUM));
        final Link rel = Link.valueOf(m.group(LINK_MATCH_GROUP_REL));
        switch (rel) {
          case first:
            page.first = pageNum;
            break;
          case prev:
            page.prev = pageNum;
            break;
          case next:
            page.next = pageNum;
            break;
          case last:
            page.last = pageNum;
            break;
          default:
            Timber.w("Unknown link: %s", link);
        }
      }
    }

    return page;
  }

  protected String fetchErrorMessage(Response response) {
    switch (response.code()) {
      case 400:
      case 403:
      case 404:
        try {
          return objectify(response, ErrorDto.class).error;
        } catch (IOException ioe) {
          Timber.e(ioe, "Failed to read response body.");
        }
      default:
        //Do nothing.
    }

    String message = "unknown";
    if (response.body() != null) {
      try {
        return response.body().string();
      } catch (IOException ioe) {
        Timber.e(ioe, "Failed to read response body.");
      }
    }

    return message;
  }

  protected static enum Link {
    first, prev, next, last
  }

}
