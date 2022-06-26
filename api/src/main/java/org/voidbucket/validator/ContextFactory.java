package org.voidbucket.validator;

import org.voidbucket.validator.constraint.Constraint;

/**
 * A factory for constructing {@link Context} for use in validation.
 * Both root contexts for {@link ValidatorState}s and sub-{@link Context}s for use {@link Constraint}
 * can be constructed.
 */
public interface ContextFactory extends Cloneable {

    /**
     * Construct a {@link Context} for a {@link ValidatorState}.
     * @param state The validator state to construct a context for.
     * @return The created context.
     */
    Context createValidationContext(ValidatorState state);

    /**
     * Construct a {@link Context} for use in the evaluation of a {@link Constraint}.
     * @param parent The parent context.
     * @param constraint The {@link Constraint} to create a context for.
     * @return The created context.
     */
    Context createConstraintContext(Context parent, Constraint constraint);

}
