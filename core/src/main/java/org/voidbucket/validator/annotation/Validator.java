package org.voidbucket.validator.annotation;

import org.voidbucket.validator.constraint.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;


@Target({ METHOD })
@Retention(RUNTIME)
public @interface Validator {

    Class<?>[] value();

}
