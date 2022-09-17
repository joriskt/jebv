package org.voidbucket.validator.constraint;

public final class ReadinessEvaluators {

    public static ReadinessEvaluatorChain chain() {
        return new ReadinessEvaluatorChain();
    }

}
