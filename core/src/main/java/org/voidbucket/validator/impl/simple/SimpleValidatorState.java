package org.voidbucket.validator.impl.simple;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.voidbucket.validator.ParameterHolder;
import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintReference;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.impl.ParameterHolderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SimpleValidatorState implements ValidatorState {

    private final Map<Constraint, ConstraintStatus> statusMap;
    private final ParameterHolder parameterHolder;

    SimpleValidatorState(final Set<Constraint> constraints) {
        this(constraints, new ParameterHolderImpl());
    }

    SimpleValidatorState(final Set<Constraint> constraints,
                         final ParameterHolder parameterHolder) {
        this.statusMap = StreamEx.of(constraints)
            .mapToEntry(constraint -> ConstraintStatus.PENDING)
            .toCustomMap(HashMap::new);
        this.parameterHolder = parameterHolder;
    }

    @Override
    public ConstraintStatus getStatus(final Constraint constraint) {
        return statusMap.getOrDefault(constraint, ConstraintStatus.UNREACHABLE);
    }

    void setStatus(final Constraint constraint,
                   final ConstraintStatus status) {
        statusMap.put(constraint, status);
    }

    @Override
    public ConstraintStatus getStatus(ConstraintReference reference) {
        return EntryStream.of(statusMap)
            .filterKeys(reference::equals)
            .findFirst()
            .map(Map.Entry::getValue)
            .orElse(ConstraintStatus.UNREACHABLE);
    }

    @Override
    public ParameterHolder getParameterHolder() {
        return parameterHolder;
    }

}
