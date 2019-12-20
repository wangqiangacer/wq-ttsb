package com.jacken.wqttsbcommon.support;

/**
 * 数据检查错误父类
 *
 * @author pocketcoder
 */
public class JsonBindException extends RuntimeException {

    private static final long serialVersionUID = -5945005768251722951L;

    /**
     * Constructs a new BindException with the specified detail message as to
     * why the bind error occurred. A detail message is a String that gives a
     * specific description of this error.
     *
     * @param msg the detail message
     */
    public JsonBindException(String msg) {
        super(msg);
    }

    /**
     * Construct a new BindException with no detailed message.
     */
    public JsonBindException() {
    }
}
