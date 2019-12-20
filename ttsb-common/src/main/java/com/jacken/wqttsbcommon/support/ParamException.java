package com.jacken.wqttsbcommon.support;


import com.jacken.wqttsbcommon.utils.ExceptionEnum;

/**
 * @author pocketcoder
 */
public class ParamException extends ApiException {

    private static final long serialVersionUID = 1L;

    public ParamException(ExceptionEnum exception) {
        super(exception);
    }

    public ParamException(String code, String message) {
        super(code, message);
    }
}
