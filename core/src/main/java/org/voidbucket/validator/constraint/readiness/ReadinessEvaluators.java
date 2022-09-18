package org.voidbucket.validator.constraint.readiness;

public final class ReadinessEvaluators {

    public static ReadinessEvaluatorChain chain() {
        return new ReadinessEvaluatorChain();
    }

}
