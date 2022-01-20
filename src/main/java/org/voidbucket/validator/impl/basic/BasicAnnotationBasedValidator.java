package org.voidbucket.validator.impl.basic;

import one.util.streamex.StreamEx;
import org.voidbucket.validator.Result;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.check.CheckDefinition;
import org.voidbucket.validator.check.CheckRunner;
import org.voidbucket.validator.check.discover.AnnotationCheckDiscoverer;
import org.voidbucket.validator.check.discover.CheckDiscoverer;
import org.voidbucket.validator.check.CheckRunnerFactory;
import org.voidbucket.validator.check.runner.EmptyConstructorCheckRunnerFactory;
import org.voidbucket.validator.reflect.param.ParameterProvider;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BasicAnnotationBasedValidator implements Validator {

    private final CheckDiscoverer discoverer;
    private final CheckRunnerFactory executorFactory;

    public BasicAnnotationBasedValidator() {
        discoverer = new AnnotationCheckDiscoverer();
        executorFactory = new EmptyConstructorCheckRunnerFactory();
    }

    private <T, S> ParameterProvider buildParameterProvider(final T subject, final S state) {
        return ParameterProvider.builder()
            .type(subject.getClass(), subject)
            .type(state.getClass(), state)
            .name("subject", subject)
            .name("state", state)
            .build();
    }

    /*
        - We have to find all checks, one of which being the Jakarta Bean Validation
          check that can perform context-free checks.
        - The State object is completely sidelined. It is a second-class citizen of
          the validation framework. It is, however, trivial to include such an object.
     */

    private Set<CheckDefinition> discoverChecks(final Class<?> subject) {
        return discoverer.discover(subject);
    }

    private Map<? extends Class<?>, CheckRunner> buildRunners(final Set<CheckDefinition> definitions) {
        // TODO: Prevent or merge duplicates
        return StreamEx.of(definitions)
            .mapToEntry(CheckDefinition::getType, executorFactory::createRunner)
            .toMap();
    }

    @Override
    public <T> Result validate(T subject) {
        Objects.requireNonNull(subject);

        // First, we need to discover all checks we need to execute.
        final Set<CheckDefinition> definitions = discoverChecks(subject.getClass());

        // We have to map these definitions to actual instances.
        final Map<? extends Class<?>, CheckRunner> runnerMap = buildRunners(definitions);

        // Next, we can run all the checks against the subject?..
        for (CheckDefinition check : definitions) {
            final CheckRunner runner = runnerMap.get(check.getType());
            runner.execute(null);
        }

        return null;
    }

}
