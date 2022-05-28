package org.voidbucket.validator.constraint;

public enum ConstraintStatus {
    PENDING,
    PASSED,
    FAILED,
    SKIPPED,
    POSTPONED,
    UNREACHABLE;

    public boolean isPending() {
        return this == PENDING;
    }

    public boolean isPassed() {
        return this == PASSED;
    }

    public boolean isFailed() {
        return this == FAILED;
    }

    public boolean isSkipped() {
        return this == SKIPPED;
    }

    public boolean isPostponed() {
        return this == POSTPONED;
    }

    public boolean isUnreachable() {
        return this == UNREACHABLE;
    }

    public boolean isFinalStatus() {
        return this == PASSED ||
               this == FAILED ||
               this == SKIPPED ||
               this == UNREACHABLE;
    }

}
