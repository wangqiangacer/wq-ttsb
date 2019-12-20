package com.jacken.wqttsbcommon.support;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

public class Validator {

    private volatile static ValidatorFactory factory = null;

    /**
     * Validates all constraints on {@code object}.
     *
     * @param object object to validate
     * @param groups the group or list of groups targeted for validation (defaults
     *               to {@link Default})
     * @throws IllegalArgumentException if object is {@code null} or if {@code null} is passed to the
     *                                  varargs groups
     * @throws ValidationException      if a non recoverable error happens during the validation
     *                                  process
     */
    public static <T> void validate(T object, Class<?>... groups) {
        if (null == object) {
            throw new JsonBindException("参数不能为空");
        }

        if (null == factory) {
            synchronized (Validator.class) {
                if (null == factory) {
                    factory = Validation.buildDefaultValidatorFactory();
                }
            }
        }

        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violationSet = validator.validate(object, groups);
        if (!CollectionUtils.isEmpty(violationSet)) {
            for (ConstraintViolation<T> violation : violationSet) {
                throw new JsonBindException(violation.getMessage());

            }
        }
    }
}
