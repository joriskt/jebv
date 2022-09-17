package org.voidbucket.validator.constraint.dependency;

import lombok.extern.slf4j.Slf4j;
import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.constraint.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class DependencyReadinessEvaluator implements ReadinessEvaluator {

    public static final int PRIORITY = 1_000;

    private final Map<ConstraintReference, Dependency> dependencyMap;

    public DependencyReadinessEvaluator() {
        this.dependencyMap = new HashMap<>();
    }

    public void register(final ConstraintReference reference,
                         final Function<DependencyBuilder, Dependency> builder) {
        dependencyMap.put(reference, builder.apply(Dependency.builder()));
    }

    @Override
    public Readiness evaluate(final ValidatorState state) {
        final List<Readiness> actions = new ArrayList<>(dependencyMap.size());
        for (Dependency dependency : dependencyMap.values()) {
            final ConstraintStatus constraintStatus = state.getStatus(dependency.getReference());

            // Fail if the dependency is unresolvable.
            if (!dependency.getReadinessMap().containsKey(constraintStatus)) {
                log.warn("No ConstraintResult -> ConstraintAction mapping for key: {}", constraintStatus);
                return Readiness.NEVER;
            }

            // Return the action as specified in the action map.
            final Readiness action = dependency.getReadinessMap()
                .getOrDefault(constraintStatus, Readiness.READY);
            if (!action.isReady()) {
                actions.add(action);
            }
        }

        // Sort the actions based on priority: SKIP > FAIL > WAIT > SCHEDULE.
        actions.sort(Readiness::compareTo);

        // Grab the highest priority action as result.
        return actions.stream().findFirst().orElse(Readiness.READY);
    }

    @Override
    public int priority() {
        return PRIORITY;
    }

}
