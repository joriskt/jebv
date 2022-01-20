package org.voidbucket.validator.check.discover;

import org.voidbucket.validator.check.CheckDefinition;

import java.util.Set;

public interface CheckDiscoverer {
    Set<CheckDefinition> discover(final Class<?> type);
}
