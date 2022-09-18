package org.voidbucket.validator.scanner;

import lombok.Getter;
import org.voidbucket.validator.constraint.readiness.ReadinessEvaluatorChain;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Getter
public final class ScannedConstraintPrototype {

    private final Class<?> type;
    private final Method method;
    private final ReadinessEvaluatorChain readinessEvaluatorChain;
    private final Set<Class<?>> requiredTypes;
    private final Set<Class<?>> optionalTypes;
    private final Set<Class<?>> providedTypes;

    public ScannedConstraintPrototype(final Class<?> type,
                                      final Method method) {
        this.type = type;
        this.method = method;
        this.readinessEvaluatorChain = new ReadinessEvaluatorChain();
        this.requiredTypes = new HashSet<>();
        this.optionalTypes = new HashSet<>();
        this.providedTypes = new HashSet<>();
    }

    public ScannedConstraint build(final Object instance) {
        return new ScannedConstraint(this, instance);
    }

}
