package org.voidbucket.validator.constraint.middleware;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.constraint.ConstraintMiddleware;
import org.voidbucket.validator.constraint.ConstraintReadiness;
import org.voidbucket.validator.constraint.ConstraintReference;
import org.voidbucket.validator.constraint.ConstraintStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class DependencyMiddleware implements ConstraintMiddleware {

    public static final int PRIORITY = 1_000;

    private final Map<ConstraintReference, Dependency> dependencyMap;

    public DependencyMiddleware() {
        this.dependencyMap = new HashMap<>();
    }

    public void register(final ConstraintReference reference,
                         final Function<DependencyBuilder, Dependency> builder) {
        dependencyMap.put(reference, builder.apply(Dependency.builder()));
    }

    @Override
    public ConstraintReadiness evaluate(final ValidatorState state) {
        final List<ConstraintReadiness> actions = new ArrayList<>(dependencyMap.size());
        for (Dependency dependency : dependencyMap.values()) {
            final ConstraintStatus constraintStatus = state.getStatus(dependency.getReference());

            // Fail if the dependency is unresolvable.
            if (!dependency.getActionMap().containsKey(constraintStatus)) {
                log.warn("No ConstraintResult -> ConstraintAction mapping for key: {}", constraintStatus);
                return ConstraintReadiness.NEVER;
            }

            // Return the action as specified in the action map.
            final ConstraintReadiness action = dependency.getActionMap()
                .getOrDefault(constraintStatus, ConstraintReadiness.READY);
            if (!action.isReady()) {
                actions.add(action);
            }
        }

        // Sort the actions based on priority: SKIP > FAIL > WAIT > SCHEDULE.
        actions.sort(ConstraintReadiness::compareTo);

        // Grab the highest priority action as result.
        return actions.stream().findFirst().orElse(ConstraintReadiness.READY);
    }

    @Override
    public int priority() {
        return PRIORITY;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class Dependency {

        private final ConstraintReference reference;
        private final Map<ConstraintStatus, ConstraintReadiness> actionMap;

        public static DependencyBuilder builder() {
            return new DependencyBuilder();
        }

    }

    @Setter
    @Accessors(chain = true, fluent = true)
    public static final class DependencyBuilder {

        private ConstraintReference reference;
        private Map<ConstraintStatus, ConstraintReadiness> actionMap;

        public DependencyBuilder() {
            actionMap = new HashMap<>();
        }

        public DependencyBuilder action(final ConstraintStatus status,
                                        final ConstraintReadiness readiness) {
            actionMap.put(status, readiness);
            return this;
        }

        public Dependency build() {
            return new Dependency(reference, actionMap);
        }

    }

}
