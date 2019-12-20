package com.jacken.wqttsbcommon.support;


import com.jacken.wqttsbcommon.utils.ExceptionEnum;

/**
 * @author pocketcoder
 */
public class ApiException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 853323936341488949L;

    protected String code;

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
        this.code = ExceptionEnum.SYS_EXCEPTION.getCode();
    }

    public ApiException(ExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
    }

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
