package com.google.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

    boolean validate() default true;

    String[] groups() default {};
}
