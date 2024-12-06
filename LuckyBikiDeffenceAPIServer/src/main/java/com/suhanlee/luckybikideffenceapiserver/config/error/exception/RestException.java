package com.suhanlee.luckybikideffenceapiserver.config.error.exception;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private ErrorCode errorCode;

    public RestException() {
        super();
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
