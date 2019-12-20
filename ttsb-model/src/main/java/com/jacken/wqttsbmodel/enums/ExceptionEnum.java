package com.jacken.wqttsbmodel.enums;


/**
 * @author jacken
 */

public enum ExceptionEnum {

    /**
     *
     */
    SUCCESS("10000", "成功"),
    SERVERERROR("10001", "服务器异常"),
    NOT_GOODS("10002", "改选品库下没有宝贝！"),
    SYS_EXCEPTION("10009", "服务器繁忙，请稍后再试");


    private String code;
    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
