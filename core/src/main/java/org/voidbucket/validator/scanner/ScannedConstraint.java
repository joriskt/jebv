package org.voidbucket.validator.scanner;

import lombok.Getter;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ReadinessEvaluator;

import java.lang.reflect.Method;

@Getter
public final class ScannedConstraint implements Constraint {

    private final Object instance;
    private final Method method;
    private final ReadinessEvaluator readinessEvaluator;

    ScannedConstraint(final ScannedConstraintPrototype prototype,
                      final Object instance) {
        this.instance = instance;
        this.method = prototype.getMethod();
        this.readinessEvaluator = prototype.getReadinessEvaluatorChain();
    }

}
