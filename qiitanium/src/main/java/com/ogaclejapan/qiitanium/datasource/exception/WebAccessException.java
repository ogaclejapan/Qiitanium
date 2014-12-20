package com.ogaclejapan.qiitanium.datasource.exception;

import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;

public class WebAccessException extends DataAccessException {

    private final int mStatusCode;

    public WebAccessException(int statusCode, String detailMessage) {
        super(detailMessage);
        mStatusCode = statusCode;
    }

    public int getStatusCode() {
        return mStatusCode;
    }



}
