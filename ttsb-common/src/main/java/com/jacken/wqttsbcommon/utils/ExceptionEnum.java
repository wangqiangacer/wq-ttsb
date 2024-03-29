package com.jacken.wqttsbcommon.utils;


/**
 * @author pocketcoder
 */

public enum ExceptionEnum {

    /**
     *
     */
    SUCCESS("10000", "成功"),
    SERVERERROR("10001", "服务器异常"),
    PARAMEMPTYERROR("10002", "参数有误"),
    TOKENRERROR("10004", "Token失效,请重新登录"),
    SIGNERROR("10005", "签名有误"),
    OPERATION_FILE("10006", "操作失败"),
    ACCOUNT_ERROR("10007", "账号异常"),
    SMSCODE_FAILURE("10008", "验证码已失效"),
    SMSCODE_ERROR("10013", "验证码错误"),
    SYS_EXCEPTION("10009", "服务器繁忙，请稍后再试"),
    ALREADY_APPLY_ERROR("10010", "当前产品申请已满，请选择其他产品"),
    APPLY_ERROR("10011", "您已申请过本产品，请选择其他产品"),
    ILLEGAL_REQUEST("10012", "非法请求"),
    SYS_USER_NOT_EXIST("20001", "账户或密码错误"),
    NO_DATA("20002", "数据不存在"),
    IDCARD_ERROR("20003", "身份证信息有误"),
    ACCOUNT_NOT_FOUND("20004", "账号不存在"),
    NAME_REPEATS("20009", "名字重复"),

    PRODUCT_EMPTY("20005", "产品不能为空"),
    PRODUCT_DOWN("20006", "产品已下架"),
    REQUEST_TIME_FREQUENCY("30001", "您的请求太频繁，请稍后重试"),

    EXISTS_CHANNEL("40000", "请选择渠道"),
    EXISTS_NAME("40001", "名称已存在"),
    EXISTS_LEVEL("40002", "排序已存在"),

    CHECK_NEW_USER("5000", "该用户在我方未注册，撞库成功！"),
    PARAM_FORMAT_ERROR("50001", "参数有误！请检查参数名及格式是否正确"),
    CHANNEL_CODE_INVALID("50002", "渠道号无效"),
    PHONE_ALREADY_REGISTERED("50003", "失败! 该手机号已存在"),
    SYSTEM_IN_BUSY("50004", "系统繁忙！请稍后重试"),


    FAILURE("-99999", "错误代码**【-99999】");

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
