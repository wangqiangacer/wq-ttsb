package com.jacken.wqttsbcommon.support;


import com.jacken.wqttsbcommon.utils.ExceptionEnum;

/**
 * @author pocketcoder
 */
public class TokenException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -6642853129794871992L;

    protected String code;

    public TokenException() {
        super();
    }

    public TokenException(String message) {
        super(message);
        this.code = ExceptionEnum.SERVERERROR.getCode();
    }

    public TokenException(ExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
    }

    public TokenException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
