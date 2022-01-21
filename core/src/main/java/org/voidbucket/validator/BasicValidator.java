package org.voidbucket.validator;

import lombok.Getter;
import one.util.streamex.StreamEx;
import org.voidbucket.validator.check.Check;
import org.voidbucket.validator.check.discover.CheckDiscoverer;
import org.voidbucket.validator.check.discover.CheckReference;
import org.voidbucket.validator.check.provider.CheckProvider;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class BasicValidator implements Validator {

    private final CheckDiscoverer discoverer;
    private final CheckProvider provider;

    public BasicValidator(final CheckDiscoverer discoverer,
                          final CheckProvider provider) {
        this.discoverer = discoverer;
        this.provider = provider;
    }

    @Override
    public <T> Result validate(final T subject) {
        // Discover all checks.
        final List<CheckReference> checkReferences = discoverer.discover(subject.getClass());

        // Extract all distinct CheckerTypes.
        final Set<Class<?>> checkTypes = checkReferences.stream()
            .map(CheckReference::getType)
            .collect(Collectors.toSet());

        // Obtain instances for these checkers.
        final Map<Class<?>, Check> checkInstances = StreamEx.of(checkTypes)
            .toMap(provider::provide);

        // For every check reference, traverse the node and validate that field.
        for (final CheckReference reference : checkReferences) {
            final Check check = checkInstances.get(reference.getType());
            Objects.requireNonNull(check);

            check.check(null);
        }

        return null;
    }

}
