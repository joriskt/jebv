package org.voidbucket.validator.constraint.middleware;

import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.constraint.ConstraintReadiness;
import org.voidbucket.validator.constraint.ConstraintMiddleware;
import org.voidbucket.validator.constraint.ConstraintMiddlewareChain;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

public class ConstraintMiddlewareChainImpl implements ConstraintMiddlewareChain {

    private final SortedSet<ConstraintMiddleware> evaluators;

    public ConstraintMiddlewareChainImpl() {
        this.evaluators = new TreeSet<>();
    }

    @Override
    public ConstraintReadiness evaluate(final ValidatorState state) {
        for (final ConstraintMiddleware evaluator : evaluators) {
            final ConstraintReadiness readiness = evaluator.evaluate(state);
            if (!readiness.isReady()) {
                return readiness;
            }
        }
        return ConstraintReadiness.READY;
    }

    @Override
    public void register(final ConstraintMiddleware evaluator) {
        if (contains(evaluator.getClass())) {
            throw new IllegalArgumentException("Cannot register ConstraintReadinessEvaluator twice: " +
                                               evaluator.getClass().getCanonicalName());
        }
        evaluators.add(evaluator);
    }

    @Override
    public boolean unregister(Class<? extends ConstraintMiddleware> middlewareType) {
        return evaluators.removeIf(evaluator -> evaluator.getClass().equals(middlewareType));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ConstraintMiddleware> T find(Class<T> middlewareType) {
        return (T) evaluators.stream()
            .filter(evaluator -> middlewareType.isAssignableFrom(evaluator.getClass()))
            .findFirst()
            .orElse(null);
    }

    @Override
    public <T extends ConstraintMiddleware> T findOrRegister(Class<T> middlewareType, Supplier<T> supplier) {
        T found = find(middlewareType);
        if (found == null) {
            found = supplier.get();
            evaluators.add(found);
        }
        return found;
    }

    @Override
    public boolean contains(Class<? extends ConstraintMiddleware> middlewareType) {
        return find(middlewareType) != null;
    }

}
