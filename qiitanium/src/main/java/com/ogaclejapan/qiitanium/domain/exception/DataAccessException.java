package com.ogaclejapan.qiitanium.domain.exception;

public class DataAccessException extends ApplicationException {

    public DataAccessException(String detailMessage) {
        super(detailMessage);
    }

    public DataAccessException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DataAccessException(Throwable throwable) {
        super(throwable);
    }

}
