package com.ogaclejapan.qiitanium.domain.exception;

public class ApplicationException extends RuntimeException {

  public ApplicationException(String detailMessage) {
    super(detailMessage);
  }

  public ApplicationException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  public ApplicationException(Throwable throwable) {
    super(throwable);
  }

}
