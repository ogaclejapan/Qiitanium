package com.ogaclejapan.qiitanium.datasource.exception;

import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;

public class WebAccessException extends DataAccessException {

  private final int statusCode;

  public WebAccessException(int statusCode, String detailMessage) {
    super(detailMessage);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

}
