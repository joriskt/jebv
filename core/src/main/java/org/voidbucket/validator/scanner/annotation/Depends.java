package org.voidbucket.validator.scanner.annotation;

import org.voidbucket.validator.constraint.Readiness;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.scanner.mutator.DependsPrototypeMutator;
import org.voidbucket.validator.scanner.mutator.Mutator;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Indicates that the method
 */
@Target(METHOD)
@Retention(RUNTIME)
@Repeatable(Depends.List.class)
@Mutator(appliedBy = DependsPrototypeMutator.class)
public @interface Depends {

    /**
     * The constraint references that this constraint depends on.
     */
    Class<?>[] value();

    /**
     * The permitted statusses.
     */
    ConstraintStatus[] permitted() default { ConstraintStatus.PASSED };

    /**
     * The state this constraint gets when the dependencies are unmet.
     */
    Readiness otherwise() default Readiness.FAIL;

    @Target(METHOD)
    @Retention(RUNTIME)
    @interface List {

        Depends[] value();

    }

}
