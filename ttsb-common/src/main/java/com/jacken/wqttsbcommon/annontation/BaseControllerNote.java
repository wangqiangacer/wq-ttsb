package com.jacken.wqttsbcommon.annontation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseControllerNote {

    /**
     * 是否校验token
     *
     * @return
     */
    boolean checkToken() default true;

    /**
     * 是否做参数校验
     *
     * @return
     */
    boolean checkParameter() default true;


    /**
     * 是否做签名校验
     *
     * @return
     */
    boolean checkSign() default true;

    /**
     * 参数类class
     *
     * @return
     */
    Class<?> beanClazz() default Object.class;

}
