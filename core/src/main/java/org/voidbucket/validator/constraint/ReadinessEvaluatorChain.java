package org.voidbucket.validator.constraint;

import org.voidbucket.validator.ValidatorState;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

public class ReadinessEvaluatorChain implements ReadinessEvaluator {

    private final SortedSet<ReadinessEvaluator> readinessMiddlewares;

    public ReadinessEvaluatorChain() {
        this.readinessMiddlewares = new TreeSet<>();
    }

    @Override
    public Readiness evaluate(final ValidatorState state) {
        for (final ReadinessEvaluator evaluator : readinessMiddlewares) {
            final Readiness readiness = evaluator.evaluate(state);
            if (!readiness.isReady()) {
                return readiness;
            }
        }
        return Readiness.READY;
    }

    public void register(final ReadinessEvaluator evaluator) {
        if (contains(evaluator.getClass())) {
            throw new IllegalArgumentException("Cannot register ConstraintReadinessEvaluator twice: " +
                                               evaluator.getClass().getCanonicalName());
        }
        readinessMiddlewares.add(evaluator);
    }

    public boolean unregister(Class<? extends ReadinessEvaluator> middlewareType) {
        return readinessMiddlewares.removeIf(evaluator -> evaluator.getClass().equals(middlewareType));
    }

    @SuppressWarnings("unchecked")
    public <T extends ReadinessEvaluator> T find(Class<T> middlewareType) {
        return (T) readinessMiddlewares.stream()
            .filter(evaluator -> middlewareType.isAssignableFrom(evaluator.getClass()))
            .findFirst()
            .orElse(null);
    }

    public <T extends ReadinessEvaluator> T findOrRegister(Class<T> middlewareType, Supplier<T> supplier) {
        T found = find(middlewareType);
        if (found == null) {
            found = supplier.get();
            readinessMiddlewares.add(found);
        }
        return found;
    }

    public boolean contains(Class<? extends ReadinessEvaluator> middlewareType) {
        return find(middlewareType) != null;
    }

}
