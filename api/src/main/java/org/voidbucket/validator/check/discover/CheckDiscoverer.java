package org.voidbucket.validator.check.discover;

import java.util.List;

/**
 * Scans a class for references to Checks, returning all found CheckReferences.
 */
public interface CheckDiscoverer {

    List<CheckReference> discover(final Class<?> subjectType);

}
