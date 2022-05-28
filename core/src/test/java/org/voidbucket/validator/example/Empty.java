package org.voidbucket.validator.example;

/**
 * An empty class, used for testing.
 */
public final class Empty {

    private Empty() {
    }

    private final static Empty INSTANCE;

    static {
        INSTANCE = new Empty();
    }

    public static Empty instance() {
        return INSTANCE;
    }

}
