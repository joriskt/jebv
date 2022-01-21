package org.voidbucket.validator.check.provider;

import org.voidbucket.validator.check.Check;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class StaticCheckProvider implements CheckProvider {

    private final Map<Class<?>, Check> checks;

    public StaticCheckProvider() {
        this.checks = new HashMap<>();
    }

    public StaticCheckProvider register(final Check check) {
        Objects.requireNonNull(check);
        this.checks.put(check.getClass(), check);
        return this;
    }

    @Override
    public Check provide(final Class<?> checkType) {
        if (!checks.containsKey(checkType)) {
            throw new IllegalArgumentException("No checker for type: " + checkType.getName());
        }
        return checks.get(checkType);
    }

}
