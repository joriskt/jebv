package org.voidbucket.validator.constraint;

/**
 * Describes the <b>readiness</b> of a {@link Constraint}.
 */
public enum ConstraintReadiness implements Comparable<ConstraintReadiness> {

    /**
     * The constraint should be skipped. Takes priority over {@link #FAIL}.
     */
    SKIP,

    /**
     * The constraint should be marked as failed.
     */
    FAIL,

    /**
     * The constraint should not be run yet, presumably due to some dependency not being met.
     */
    WAIT,

    /**
     * The constraint should be scheduled for evaluation.
     */
    READY,

    /**
     * This constraint will never become {@link #READY}.
     */
    NEVER;

    public boolean isSkip() {
        return this == SKIP;
    }

    public boolean isFail() {
        return this == FAIL;
    }

    public boolean isWait() {
        return this == WAIT;
    }

    public boolean isReady() {
        return this == READY;
    }

    public boolean isNever() {
        return this == NEVER;
    }

    public ConstraintStatus toStatus() {
        return switch (this) {
            case READY -> ConstraintStatus.PENDING;
            case FAIL -> ConstraintStatus.FAILED;
            case WAIT -> ConstraintStatus.POSTPONED;
            case SKIP -> ConstraintStatus.SKIPPED;
            case NEVER -> ConstraintStatus.UNREACHABLE;
        };
    }

}
