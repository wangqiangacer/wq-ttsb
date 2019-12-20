package com.jacken.wqttsbcommon.annontation;



import com.jacken.wqttsbcommon.enums.DateEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 *
 * @author wy
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

    String category() default "";

    String key();

    /**
     * 过期时间数值，默认-1为永久
     *
     * @return
     */
    int expire() default -1;

    /**
     * 时间单位，默认为秒
     *
     * @return
     */
    DateEnum dateType() default DateEnum.SECONDS;

    /**
     * 删除缓存
     *
     * @return
     */
    boolean delCache() default false;
}
