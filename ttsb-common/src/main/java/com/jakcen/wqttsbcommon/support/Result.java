package com.jakcen.wqttsbcommon.support;


import com.jakcen.wqttsbcommon.utils.ExceptionEnum;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class Result<T> implements Serializable {

    private static final String SUCCESS = ExceptionEnum.SUCCESS.getCode();
    private static final String MESSAGE = ExceptionEnum.SUCCESS.getMessage();

    private String code;

    private String message;

    private T data = null;

    public Result() {
        this.code = SUCCESS;
        this.message = MESSAGE;
    }

    public Result(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public Result(ExceptionEnum exceptionEnum, T t) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(T data) {
        this.code = SUCCESS;
        this.message = MESSAGE;
        this.data = data;
    }

    public boolean isOk() {
        return this.code.equals(SUCCESS);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> success(T t) {
        return new Result<>(t);
    }

    public static Result success() {
        return new Result(ExceptionEnum.SUCCESS);
    }

    public static Result error(ExceptionEnum exceptionEnum) {
        return new Result(exceptionEnum);
    }
}
